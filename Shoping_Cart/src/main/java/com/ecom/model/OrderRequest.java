package com.ecom.model;

import lombok.Data;

@Data

public class OrderRequest {

   private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pincode;
	
	private String mobileNumber;
	
	private String paymenetType;



	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPaymenetType() {
		return paymenetType;
	}

	public void setPaymenetType(String paymenetType) {
		this.paymenetType = paymenetType;
	}
	@Override
	public String toString() {
		return "OrderRequest [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", address="
				+ address + ", city=" + city + ", state=" + state + ", pincode=" + pincode + ", mobileNumber="
				+ mobileNumber + ", paymenetType=" + paymenetType + "]";
	}

	
}
