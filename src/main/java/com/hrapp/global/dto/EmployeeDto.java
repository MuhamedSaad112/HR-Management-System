package com.hrapp.global.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

	private Long empId;

	@NotEmpty
	private String empFirstName;

	@NotEmpty
	private String empLastName;

	private String fullName;

	@Email
	private String empEmail;

	private String empPhoneNumber;

	private Instant empHireDate;

	@Max(150000)
	@Min(12000)
	private Long empSalary;

	private Long empCommissionPct;

}
