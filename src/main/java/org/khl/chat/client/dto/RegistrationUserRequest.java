package org.khl.chat.client.dto;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class RegistrationUserRequest {


	private String name;
	private String email;
	private String  password;	
	private String  role;

	public RegistrationUserRequest(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = "user";
	}
	
	public String getJson() {
		JSONObject jobj = new JSONObject();
		jobj.put("name", new JSONString(this.name));
		jobj.put("email", new JSONString(this.email));
		jobj.put("password", new JSONString(this.password));
		jobj.put("role", new JSONString(this.role));

		return jobj.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
