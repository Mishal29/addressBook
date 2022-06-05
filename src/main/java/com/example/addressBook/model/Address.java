package com.example.addressBook.model;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class Address {
	
	private int addressId;
	
	@NotBlank
	private String fullname;
	
	private String furigana;
	
	@NotBlank
	private String address;
	
	private String tel;
	
	@Email
	private String mail;
	
	private String note;
	
	private String userId;
	
}
