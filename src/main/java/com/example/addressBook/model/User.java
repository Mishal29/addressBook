package com.example.addressBook.model;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class User {
	
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]+$")
	private String userId;
	
	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]+$")
	private String pw;
	
	private String name;
	
}
