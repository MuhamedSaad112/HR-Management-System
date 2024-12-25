package com.hrapp.global.service;

import com.hrapp.global.dto.AdminUserDto;
import com.hrapp.global.dto.UserDto;
import com.hrapp.global.entity.Authority;
import com.hrapp.global.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for mapping between {@link User} and {@link UserDto} or {@link AdminUserDto}.
 */
@Service
public class UserMapper {

	/**
	 * Converts a list of {@link User} entities to a list of {@link UserDto} objects.
	 *
	 * @param users the list of {@link User} entities to convert.
	 * @return a list of {@link UserDto} objects.
	 */
	public List<UserDto> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
	}

	/**
	 * Converts a {@link User} entity to a {@link UserDto} object.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link UserDto} object.
	 */
	public UserDto userToUserDTO(User user) {
		return new UserDto(user);
	}

	/**
	 * Converts a list of {@link User} entities to a list of {@link AdminUserDto} objects.
	 *
	 * @param users the list of {@link User} entities to convert.
	 * @return a list of {@link AdminUserDto} objects.
	 */
	public List<AdminUserDto> usersToAdminUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).collect(Collectors.toList());
	}

	/**
	 * Converts a {@link User} entity to a {@link AdminUserDto} object.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link AdminUserDto} object.
	 */
	public AdminUserDto userToAdminUserDTO(User user) {
		return new AdminUserDto(user);
	}

	/**
	 * Converts a list of {@link AdminUserDto} objects to a list of {@link User} entities.
	 *
	 * @param userDTOs the list of {@link AdminUserDto} objects to convert.
	 * @return a list of {@link User} entities.
	 */
	public List<User> userDTOsToUsers(List<AdminUserDto> userDTOs) {
		return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
	}

	/**
	 * Converts an {@link AdminUserDto} object to a {@link User} entity.
	 *
	 * @param userDTO the {@link AdminUserDto} object to convert.
	 * @return the corresponding {@link User} entity.
	 */
	public User userDTOToUser(AdminUserDto userDTO) {
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
	 * Converts a {@link User} entity to a {@link UserDto} object with only the ID.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link UserDto} object containing only the ID.
	 */
	@Named("id")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public UserDto toDtoId(User user) {
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		return userDto;
	}

	/**
	 * Converts a set of {@link User} entities to a set of {@link UserDto} objects containing only the ID.
	 *
	 * @param users the set of {@link User} entities to convert.
	 * @return a set of {@link UserDto} objects containing only the ID.
	 */
	@Named("idSet")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public Set<UserDto> toDtoIdSet(Set<User> users) {
		if (users == null) {
			return Collections.emptySet();
		}

		Set<UserDto> userSet = new HashSet<>();
		for (User userEntity : users) {
			userSet.add(this.toDtoId(userEntity));
		}

		return userSet;
	}

	/**
	 * Converts a {@link User} entity to a {@link UserDto} object containing both the ID and login.
	 *
	 * @param user the {@link User} entity to convert.
	 * @return the corresponding {@link UserDto} object containing ID and login.
	 */
	@Named("login")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "login", source = "login")
	public UserDto toDtoLogin(User user) {
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setLogin(user.getLogin());
		return userDto;
	}

	/**
	 * Converts a set of {@link User} entities to a set of {@link UserDto} objects containing both the ID and login.
	 *
	 * @param users the set of {@link User} entities to convert.
	 * @return a set of {@link UserDto} objects containing ID and login.
	 */
	@Named("loginSet")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "login", source = "login")
	public Set<UserDto> toDtoLoginSet(Set<User> users) {
		if (users == null) {
			return Collections.emptySet();
		}

		Set<UserDto> userSet = new HashSet<>();
		for (User userEntity : users) {
			userSet.add(this.toDtoLogin(userEntity));
		}

		return userSet;
	}
}
