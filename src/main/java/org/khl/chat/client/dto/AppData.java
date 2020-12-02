package org.khl.chat.client.dto;

import org.khl.chat.client.LoginService;
import org.khl.chat.client.LoginServiceAsync;
import org.khl.chat.client.component.Grid;
import org.khl.chat.client.component.LoginPage;
import org.khl.chat.client.component.MainPage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.Window;

public enum AppData {
	
	INSTANCE;
	
	private UserDto userDto;
//    private Long expMillis;
	private String token;
	private Long chatId;
	
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	
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
	
	public void checkToken() {
		
		loginService.getTokenFromSession(new AsyncCallback<String>() { 
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Что-то пошло не так");
			}
			
			@Override
			public void onSuccess(String token) {
				if (token != null) {
					AppData.INSTANCE.setToken(token);
					GWT.debugger();
					RootPanel.get().clear();
					RootPanel.get().add(new MainPage());
				}
				else {
					RootPanel.get().add(new LoginPage());
				}
			}
		});
	}
	
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
	
	public Long getChatId() {
		return chatId;
	}
	
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
}
