package org.khl.chat.client;

import org.khl.chat.client.dto.UserDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{

    void login(String token);
 
    boolean tokenExist();
 
    void logout();
    
    String getTokenFromSession();
}
