package org.khl.chat.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.khl.chat.client.LoginService;
import org.khl.chat.client.dto.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService
{

	private static final long serialVersionUID = 1L;

	@Override
    public void login(String token)
    {
        setTokenIntoSession(token);
    }
 
    @Override
    public boolean tokenExist()
    {
        if (getTokenFromSession() != null)
			return true;
			else return false;
    }
 
    @Override
    public void logout()
    {
        deleteUserFromSession();
    }
 
    public String getTokenFromSession()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        return token;
    }
 
    private void setTokenIntoSession(String token)
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("token", token);
    }
 
    private void deleteUserFromSession()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("token");
    }
}
