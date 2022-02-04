package com.example.second.models;


import lombok.*;



public class UserSideResponse {

	
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String mobileNumber;
	private String email;
	private String cAddress;
	private String pAddress;

//	public UserSideResponse() {
//
//	}

	public UserSideResponse(String userName, String firstName, String lastName, String password, String mobileNumber, String email, String cAddress, String pAddress) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.cAddress = cAddress;
		this.pAddress = pAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getcAddress() {
		return cAddress;
	}

	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}
}