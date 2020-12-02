package org.khl.chat.client;

import org.khl.chat.client.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	
    void login(String token, AsyncCallback<Void> callback);

	void tokenExist(AsyncCallback<Boolean> callback);

	void logout(AsyncCallback<Void> callback);
	
	void getTokenFromSession(AsyncCallback<String> callback);
}
