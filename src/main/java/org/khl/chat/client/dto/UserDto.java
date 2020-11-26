package org.khl.chat.client.dto;

import com.google.gwt.json.client.JSONObject;

public class UserDto {
	
	private Long id;
	private String name;
	private String email;
	private String  password;	
	private String  role;
	
	
	public UserDto() {}
			
	public UserDto(Long id, String name, String email,String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		//this.password = password;
		this.role = role;
	}
	
	public static UserDto fromJson(JSONObject jobj) {
		Long id = (long) jobj.get("id").isNumber().doubleValue();
		String name = jobj.get("name").isString().stringValue();
		String email = jobj.get("email").isString().stringValue();
		String role = jobj.get("role").isString().stringValue();
		
		return new UserDto(id, name, email, role);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
