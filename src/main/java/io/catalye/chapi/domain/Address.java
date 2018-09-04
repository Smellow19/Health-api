package io.catalye.chapi.domain;

public class Address {
	String street;
	String city;
	String state;
	String postal;

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPostal() {
		return postal;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", state=" + state + ", postal=" + postal + "]";
	}

}
