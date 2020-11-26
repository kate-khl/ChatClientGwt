package org.khl.chat.client.dto;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class LoginResponseDto {
	
	private String token;
	private UserDto userDto;
	
	public LoginResponseDto(String token, UserDto userDto) {
		super();
		this.token = token;
		this.userDto = userDto;
	}
	
	public static LoginResponseDto fromJson (String json) {
		JSONValue jo = JSONParser.parseStrict(json);
		String token = jo.isObject().get("token").isString().stringValue();
		UserDto userDto = UserDto.fromJson(jo.isObject().get("userDto").isObject());
		
		return new LoginResponseDto(token, userDto);
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto status) {
		this.userDto = status;
	}

	
	
}
