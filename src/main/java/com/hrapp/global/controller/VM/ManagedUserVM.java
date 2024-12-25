package com.hrapp.global.controller.VM;


import com.hrapp.global.dto.AdminUserDto;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user
 * management UI.
 */
@NoArgsConstructor // Empty constructor needed for Jackson.
public class ManagedUserVM extends AdminUserDto {

	public static final int PASSWORD_MIN_LENGTH = 4;

	public static final int PASSWORD_MAX_LENGTH = 100;

	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// prettier-ignore
	
	@Override
	public String toString() {
		return "ManagedUserVM{" + super.toString() + "} ";
	}
}
