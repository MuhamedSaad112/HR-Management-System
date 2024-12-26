package com.hrapp.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrapp.global.config.Constants;
import com.hrapp.global.entity.Authority;
import com.hrapp.global.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
@NoArgsConstructor
@Getter
@Setter
public class AdminUserDto {

	private Long id;

	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 254)
	private String email;

	@Size(min = 2, max = 10)
	private String langKey;

	@Size(max = 256)
	private String imageUrl;

	private boolean activated = false;

	private String createdBy;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Instant createdDate;

	private String lastModifiedBy;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Instant lastModifiedDate;

	private Set<String> authorities;

	public AdminUserDto(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.langKey = user.getLangKey();
		this.activated = user.isActivated();
		this.imageUrl = user.getImageUrl();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.lastModifiedBy = user.getLastModifiedBy();
		this.lastModifiedDate = user.getLastModifiedDate();
		this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
	}

}
