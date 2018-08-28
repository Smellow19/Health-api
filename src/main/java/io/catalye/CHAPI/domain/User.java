package io.catalye.CHAPI.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
	@Id
	String id;
	String name;
	String title;
	ArrayList<String> roles;
	String email;
	String password;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", title=" + title + ", roles=" + roles + ", emailAddress=" + email
				+ ", password=" + password + "]";
	}

	public ArrayList<String> addRoles(String role1, String role2) {
		ArrayList<String> userRoles = new ArrayList<String>();
		userRoles.add(role1);
		userRoles.add(role2);
		return userRoles;

	}

}
