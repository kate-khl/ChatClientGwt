package org.khl.chat.client.dto;

import javax.validation.constraints.NotNull;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class LoginRequestDto {
	
	@NotNull
	private String email;
	private String password;
	
	public LoginRequestDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
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
	public String getJson() {
		JSONObject jobj = new JSONObject();
		jobj.put("email", new JSONString(this.email));
		jobj.put("password", new JSONString(this.password));

		return jobj.toString();
	}
}
