package com.hrapp.global.service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.hrapp.global.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Service class for handling email-related operations, such as sending
 * activation emails, password reset emails, and account creation notifications.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class MailService {

	// Constants for template variable keys
	private static final String USER = "user";

	@Value("${mail.from}") // Email address for sending emails
	private String mailFrom;

	@Value("${mail.baseUrl}") // Base URL used in email templates
	private String baseUrl;

	// Dependencies injected via constructor
	private final JavaMailSender javaMailSender; // Mail sender utility
	private final MessageSource messageSource; // Used to retrieve localized messages
	private final SpringTemplateEngine templateEngine; // For processing email templates

	/**
	 * Sends a basic email with the specified parameters.
	 *
	 * @param to          Recipient email address
	 * @param subject     Email subject
	 * @param content     Email content
	 * @param isMultipart Whether the email has attachments
	 * @param isHtml      Whether the content is HTML or plain text
	 */
	@Async
	public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {

		log.debug("Send Email[Multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart,
				isHtml, to, subject, content);

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			// Configure email message
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, isMultipart,
					StandardCharsets.UTF_8.name());
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, isHtml);
			messageHelper.setFrom(mailFrom);

			// Send the email
			javaMailSender.send(mimeMessage);
			log.debug("Sent Email to User '{}'", to);

		} catch (Exception e) {
			log.warn("Email does not exist for user '{}'", to, e);
		}
	}

	/**
	 * Sends an email using a specified template.
	 *
	 * @param user         The user to whom the email is sent
	 * @param templateName The name of the email template
	 * @param titleKey     The key for the email subject (localized)
	 * @param link         The dynamic link to include in the email
	 */
	@Async
	public void sendEmailFromTemplate(User user, String templateName, String titleKey, String link) {

		if (user.getEmail() == null) {
			log.debug("Email does not exist for user '{}'", user.getLogin());
			return;
		}

		// Set the email context with user details and link
		Locale locale = Locale.forLanguageTag(user.getLangKey());
		Context context = new Context(locale);
		context.setVariable(USER, user);
		context.setVariable("link", link);

		// Process the email template and retrieve the content
		String content = templateEngine.process(templateName, context);

		// Retrieve the subject from the message source
		String subject = messageSource.getMessage(titleKey, null, locale);

		// Send the email
		sendEmail(user.getEmail(), subject, content, false, true);
	}

	/**
	 * Sends an activation email to the user with a specific activation link.
	 *
	 * @param user The user to whom the activation email is sent
	 */
	@Async
	public void sendActivationEmail(User user) {
		log.debug("Send activation email to '{}'", user.getEmail());

		// Generate the activation link
		String activationLink = baseUrl + "/api/v1/activate?key=" + user.getActivationKey();
		log.debug("Generated activation link: '{}'", activationLink);

		// Send the activation email using the specified template
		sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title", activationLink);
	}

	/**
	 * Sends an account creation email to the user.
	 *
	 * @param user The user to whom the creation email is sent
	 */
	@Async
	public void sendCreationEmail(User user) {
		log.debug("Sending account creation email to '{}'", user.getEmail());

		// Generate the login link
		String creationLink = baseUrl + "/login";
		log.debug("Generated creation link: '{}'", creationLink);

		// Send the creation email using the specified template
		sendEmailFromTemplate(user, "mail/creationEmail", "email.creation.title", creationLink);
	}

	/**
	 * Sends a password reset email to the user with a reset link.
	 *
	 * @param user The user to whom the password reset email is sent
	 */
	@Async
	public void sendPasswordResetEmail(User user) {
		log.debug("Sending password reset email to '{}'", user.getEmail());

		// Generate the password reset link
		String resetLink = baseUrl + "/api/v1/account/reset-password/finish?key=" + user.getResetKey();
		log.debug("Generated password reset link: '{}'", resetLink);

		// Send the password reset email using the specified template
		sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title", resetLink);
	}
}
