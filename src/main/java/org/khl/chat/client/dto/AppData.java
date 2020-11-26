package org.khl.chat.client.dto;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public enum AppData {
	 
	INSTANCE;
	
	private UserDto userDto;
//    private Long expMillis;
    private String token;
    private Long chatId;
    
    public void setFields(UserDto userDto, Long expMillis, String token, Long chatId) {
    	this.userDto = userDto;
//		this.expMillis = expMillis;
		this.token = token;
		this.chatId = chatId;
	}
    
    public void setFieldsFromJson(String json) {
		JSONValue jo = JSONParser.parseStrict(json);
		this.token = jo.isObject().get("token").isString().stringValue();
		this.userDto = UserDto.fromJson(jo.isObject().get("userDto").isObject());
    }
    
//    public boolean validToken() {
//    	if(this.token != null && this.expMillis > System.currentTimeMillis())
//    		return true;
//    	else return false;
//    }
    
	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
//	public Long getExpMillis() {
//		return expMillis;
//	}
//
//	public void setExpMillis(Long expDate) {
//		this.expMillis = expDate;
//	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
}
