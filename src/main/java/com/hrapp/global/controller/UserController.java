package com.hrapp.global.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.hrapp.global.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hrapp.global.config.Constants;
import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.controller.errors.EmailAlreadyUsedException;
import com.hrapp.global.controller.errors.LoginAlreadyUsedException;
import com.hrapp.global.dto.AdminUserDTO;
import com.hrapp.global.entity.User;
import com.hrapp.global.repository.UserRepository;
import com.hrapp.global.security.AuthoritiesConstants;
import com.hrapp.global.service.MailService;
import com.hrapp.global.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing users. Another option would be to have a
 * specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Log4j2
public class UserController {

	private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections
			.unmodifiableList(Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey",
					"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"));

	@Value("${properties.clientApp.name}")
	private String applicationName;

	private final UserService userService;

	private final UserRepository userRepository;

	private final MailService mailService;

	/**
	 * {@code POST  /admin/users} : Creates a new user.
	 * <p>
	 * Creates a new user if the login and email are not already used, and sends an
	 * mail with an activation link. The user needs to be activated on creation.
	 *
	 * @param userDTO the user to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new user, or with status {@code 400 (Bad Request)} if the
	 *         login or email is already in use.
	 * @throws URISyntaxException       if the Location URI syntax is incorrect.
	 * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or
	 *                                  email is already in use.
	 */

	@PostMapping("/users")
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<User> createUser(@Valid @RequestBody AdminUserDTO userDTO) throws URISyntaxException {
		log.debug("REST request to save User : {}", userDTO);

		if (userDTO.getId() != null) {
			throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
			// Lowercase the user login before comparing with database
		} else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
			throw new LoginAlreadyUsedException();
		} else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
			throw new EmailAlreadyUsedException();
		} else {
			User newUser = userService.createUser(userDTO);
			mailService.sendCreationEmail(newUser);
			return ResponseEntity.created(new URI("/api/admin/users/" + newUser.getLogin()))
					.headers(HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getLogin()))
					.body(newUser);
		}
	}

	/**
	 * {@code PUT /admin/users} : Updates an existing User.
	 *
	 * @param userDTO the user to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated user.
	 * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
	 *                                   already in use.
	 * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is
	 *                                   already in use.
	 */
	@PutMapping("/users")
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<AdminUserDTO> updateUser(@Valid @RequestBody AdminUserDTO userDTO) {
		log.debug("REST request to update User : {}", userDTO);
		Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new EmailAlreadyUsedException();
		}
		existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new LoginAlreadyUsedException();
		}
		Optional<AdminUserDTO> updatedUser = userService.updateUser(userDTO);

		return ResponseUtil.wrapOrNotFound(updatedUser,
				HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin()));
	}


	/**
	 * {@code GET /admin/users} : get all users with all the details - calling this
	 * are only allowed for the administrators.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         all users.
	 */
	@GetMapping("/users")
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<List<AdminUserDTO>> getAllUsers(Pageable pageable) {
		log.debug("REST request to get all User for an admin");
		if (!onlyContainsAllowedProperties(pageable)) {
			return ResponseEntity.badRequest().build();
		}

		final Page<AdminUserDTO> page = userService.getAllManagedUsers(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	private boolean onlyContainsAllowedProperties(Pageable pageable) {
		return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
	}

	/**
	 * {@code GET /admin/users/:login} : get the "login" user.
	 *
	 * @param login the login of the user to find.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the "login" user, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/users/{login}")
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<AdminUserDTO> getUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
		log.debug("REST request to get User : {}", login);

		Optional<User> user = userService.getUserWithAuthoritiesByLogin(login);

		if (user.isPresent()) {
			AdminUserDTO userDTO = new AdminUserDTO(user.get());
			return ResponseEntity.ok(userDTO);
		} else {

			throw new ResourceNotFoundException("User with login " + login + " not found");
			// return ResponseEntity.notFound().build();
		}
	}

	/**
	 * {@code DELETE /admin/users/:login} : delete the "login" User.
	 *
	 * @param login the login of the user to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/users/{login}")
	@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
		log.debug("REST request to delete User: {}", login);
		Optional<User> user = userService.getUserWithAuthoritiesByLogin(login);
		if (user.isPresent()) {
			userService.deleteUser(login);
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", login)).build();
		} else {

			throw new ResourceNotFoundException("User with login " + login + " not found");
		}
	}
}