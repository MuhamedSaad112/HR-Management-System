package com.hrapp.global.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.hrapp.global.dto.AdminUserDTO;
import com.hrapp.global.dto.UserDTO;
import com.hrapp.global.entity.Authority;
import com.hrapp.global.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping between {@link User} and {@link UserDTO} or {@link AdminUserDTO}.
 */
@Service
public class UserMapper {

	/**
	 * Converts a list of {@link User} entities to a list of {@link UserDTO} objects.
	 *
	 * @param users the list of {@link User} entities to convert.
	 * @return a list of {@link UserDTO} objects.
	 */
	public List<UserDTO> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
	}

	/**
	 * Converts a {@link User} entity to a {@link UserDTO} object.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link UserDTO} object.
	 */
	public UserDTO userToUserDTO(User user) {
		return new UserDTO(user);
	}

	/**
	 * Converts a list of {@link User} entities to a list of {@link AdminUserDTO} objects.
	 *
	 * @param users the list of {@link User} entities to convert.
	 * @return a list of {@link AdminUserDTO} objects.
	 */
	public List<AdminUserDTO> usersToAdminUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).collect(Collectors.toList());
	}

	/**
	 * Converts a {@link User} entity to a {@link AdminUserDTO} object.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link AdminUserDTO} object.
	 */
	public AdminUserDTO userToAdminUserDTO(User user) {
		return new AdminUserDTO(user);
	}

	/**
	 * Converts a list of {@link AdminUserDTO} objects to a list of {@link User} entities.
	 *
	 * @param userDTOs the list of {@link AdminUserDTO} objects to convert.
	 * @return a list of {@link User} entities.
	 */
	public List<User> userDTOsToUsers(List<AdminUserDTO> userDTOs) {
		return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
	}

	/**
	 * Converts an {@link AdminUserDTO} object to a {@link User} entity.
	 *
	 * @param userDTO the {@link AdminUserDTO} object to convert.
	 * @return the corresponding {@link User} entity.
	 */
	public User userDTOToUser(AdminUserDTO userDTO) {
		if (userDTO == null) {
			return null;
		} else {
			User user = new User();
			user.setId(userDTO.getId());
			user.setLogin(userDTO.getLogin());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setImageUrl(userDTO.getImageUrl());
			user.setActivated(userDTO.isActivated());
			Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
			user.setAuthorities(authorities);
			return user;
		}
	}

	/**
	 * Converts a set of strings representing authorities to a set of {@link Authority} objects.
	 *
	 * @param authoritiesAsString the set of strings representing authorities.
	 * @return a set of {@link Authority} objects.
	 */
	private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
		Set<Authority> authorities = new HashSet<>();

		if (authoritiesAsString != null) {
			authorities = authoritiesAsString.stream().map(string -> {
				Authority auth = new Authority();
				auth.setName(string);
				return auth;
			}).collect(Collectors.toSet());
		}

		return authorities;
	}

	/**
	 * Converts an ID to a {@link User} entity with that ID.
	 *
	 * @param id the ID to set on the {@link User} entity.
	 * @return the {@link User} entity with the specified ID.
	 */
	public User userFromId(Long id) {
		if (id == null) {
			return null;
		}
		User user = new User();
		user.setId(id);
		return user;
	}

	/**
	 * Converts a {@link User} entity to a {@link UserDTO} object with only the ID.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link UserDTO} object containing only the ID.
	 */
	@Named("id")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public UserDTO toDtoId(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		return userDto;
	}

	/**
	 * Converts a set of {@link User} entities to a set of {@link UserDTO} objects containing only the ID.
	 *
	 * @param users the set of {@link User} entities to convert.
	 * @return a set of {@link UserDTO} objects containing only the ID.
	 */
	@Named("idSet")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public Set<UserDTO> toDtoIdSet(Set<User> users) {
		if (users == null) {
			return Collections.emptySet();
		}

		Set<UserDTO> userSet = new HashSet<>();
		for (User userEntity : users) {
			userSet.add(this.toDtoId(userEntity));
		}

		return userSet;
	}

	/**
	 * Converts a {@link User} entity to a {@link UserDTO} object containing both the ID and login.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link UserDTO} object containing ID and login.
	 */
	@Named("login")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "login", source = "login")
	public UserDTO toDtoLogin(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setLogin(user.getLogin());
		return userDto;
	}

	/**
	 * Converts a set of {@link User} entities to a set of {@link UserDTO} objects containing both the ID and login.
	 *
	 * @param users the set of {@link User} entities to convert.
	 * @return a set of {@link UserDTO} objects containing ID and login.
	 */
	@Named("loginSet")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "login", source = "login")
	public Set<UserDTO> toDtoLoginSet(Set<User> users) {
		if (users == null) {
			return Collections.emptySet();
		}

		Set<UserDTO> userSet = new HashSet<>();
		for (User userEntity : users) {
			userSet.add(this.toDtoLogin(userEntity));
		}

		return userSet;
	}
}
