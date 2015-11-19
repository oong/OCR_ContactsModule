package com.example.contacts.model;

public class PeopleInfo {
	private String name;
	private String tellNumber;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private String address;
	private String companyName;
	public PeopleInfo(String name, String tellNumber, String phoneNumber,
			String faxNumber, String email, String address, String companyName) {
		super();
		this.name = name;
		this.tellNumber = tellNumber;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.email = email;
		this.address = address;
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "PeopleInfo [name=" + name + ", tellNumber=" + tellNumber
				+ ", phoneNumber=" + phoneNumber + ", faxNumber=" + faxNumber
				+ ", email=" + email + ", address=" + address
				+ ", companyName=" + companyName + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTellNumber() {
		return tellNumber;
	}
	public void setTellNumber(String tellNumber) {
		this.tellNumber = tellNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}