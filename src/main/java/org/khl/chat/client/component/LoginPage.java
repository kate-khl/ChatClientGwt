package org.khl.chat.client.component;

import org.khl.chat.client.dto.AppData;
import org.khl.chat.client.dto.LoginRequestDto;
import org.khl.chat.client.dto.LoginResponseDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginPage extends Composite {
	
	private static final String URL_SIGN_IN = "http://127.0.0.1:8080/auth";
	private static final String URL_SIGN_UP = "http://127.0.0.1:8080/registration";
	private AppData appData = AppData.INSTANCE;
	
	interface LoginPageUiBinder extends UiBinder<AbsolutePanel, LoginPage> {}

	private static LoginPageUiBinder ourUiBinder = GWT.create(LoginPageUiBinder.class);
	private Widget widget = ourUiBinder.createAndBindUi(this);
	
	@UiField
	Button btnSignIn;
	@UiField
	Button btnSignUp;
	@UiField
	TextBox tbPassword;
	@UiField
	TextBox tbEmail;
	
	public LoginPage(){
		initWidget(widget);
		btnSignIn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				LoginRequestDto loginRequest = new LoginRequestDto(tbEmail.getText(), tbPassword.getText());
				
				String jsonBody = loginRequest.getJson();
				sendSignInRest(RequestBuilder.POST, URL_SIGN_IN, jsonBody);
			}
		});
		
		btnSignUp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("hello");
			}
		});
	}
	
	 private void sendSignInRest(final RequestBuilder.Method httpMethod, final String restUrl, final String jsonString) {
	        RequestBuilder builder = new RequestBuilder(httpMethod, URL.encode(restUrl));

	        try {
	            builder.setHeader("Content-Type", "application/json");
	            builder.setRequestData(jsonString);
	            builder.setCallback( new RequestCallback() {
	                public void onError(Request request, Throwable exception) {
	                	System.out.println("lalala");
	                    Window.alert(exception.getMessage());
	                }
	                public void onResponseReceived(Request request, Response response) {
	                    if (200 == response.getStatusCode())
	                    {
		                	String responseJson = response.getText();
		                	appData.setFieldsFromJson(responseJson);

	                    } else {
	                        Window.alert("Что-то пошло не так: "+ response.getStatusText());
	                    }

	                }
	            });
	            builder.send();
	        } catch (RequestException e) {
	            System.out.println(e.getMessage());;
	        }
	 }
	 
	 private void sendSignUpRest(final RequestBuilder.Method httpMethod, final String restUrl, final String jsonString) {
		 RequestBuilder builder = new RequestBuilder(httpMethod, URL.encode(restUrl));
		 
		 try {
			 builder.setHeader("Content-Type", "application/json");
			 builder.setRequestData(jsonString);
			 builder.setCallback( new RequestCallback() {
				 public void onError(Request request, Throwable exception) {
					 Window.alert(exception.getMessage());
				 }
				 public void onResponseReceived(Request request, Response response) {
					 if (200 == response.getStatusCode())
					 {
						 String responseJson = response.getText();
						 appData.setFieldsFromJson(responseJson);
						 
						 
//		                    Window.alert(lr.getToken() + " " + lr.getUserDto().getName());
					 } else {
						 Window.alert("Что-то пошло не так: "+ response.getStatusText());
					 }
					 
				 }
			 });
			 builder.send();
		 } catch (RequestException e) {
			 System.out.println(e.getMessage());;
		 }
	 }
}

