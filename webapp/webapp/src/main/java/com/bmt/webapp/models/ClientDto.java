package com.bmt.webapp.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class ClientDto {
	@NotEmpty(message="The First name is required")
	private String firstname;
	
	@NotEmpty(message="The Last name is required")
	private String lastname;
	
	@NotEmpty(message="The Email is required")
	@Email
	private String email;
	
	private String phone;
	private String address;
	
	@NotEmpty(message="The status is required")
	private String status;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
