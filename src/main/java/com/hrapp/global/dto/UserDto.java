package com.hrapp.global.dto;


import com.hrapp.global.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private Long id;

	private String login;

	public UserDto(User user) {
		this.id = user.getId();
		// Customize it here if you need
		this.login = user.getLogin();
	}
}
