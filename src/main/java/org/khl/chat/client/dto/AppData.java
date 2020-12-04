package org.khl.chat.client.dto;

import java.util.Base64;

import org.khl.chat.client.LoginService;
import org.khl.chat.client.LoginServiceAsync;
import org.khl.chat.client.component.Grid;
import org.khl.chat.client.component.LoginPage;
import org.khl.chat.client.component.MainPage;
import org.khl.chat.client.utils.Base64Utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
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
					GWT.debugger();
					AppData.INSTANCE.setToken(token);
					
					JSONObject jobjBody = getBodyFromJwt(token);
					
					AppData.INSTANCE.setUserDto(UserDto.fromJson(jobjBody));
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
	
	public void logout() {
		loginService.logout(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(Void result) {
				RootPanel.get().add(new LoginPage());
				
			}
		});
	}
	
	private JSONObject getBodyFromJwt(String token) {
		
		String[] split_string = token.split("\\.");
        String base64EncodedBody = split_string[1];		
        
        String jStringBody = new String(Base64Utils.fromBase64(base64EncodedBody));
        JSONObject jobj = JSONParser.parseStrict(jStringBody.trim()).isObject();
        
        return jobj;
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
