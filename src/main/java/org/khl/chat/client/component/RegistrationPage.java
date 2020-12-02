package org.khl.chat.client.component;

import org.khl.chat.client.dto.AppData;
import org.khl.chat.client.dto.LoginRequestDto;
import org.khl.chat.client.dto.LoginResponseDto;
import org.khl.chat.client.dto.RegistrationUserRequest;
import org.khl.chat.client.dto.UserDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RegistrationPage extends Composite {
	
	private static final String URL_SIGN_IN = "http://127.0.0.1:8080/auth";
	private static final String URL_SIGN_UP = "http://127.0.0.1:8080/registration";
	private static final String CONTENT_TYPE = "Content-Type";
	private AppData appData = AppData.INSTANCE;
	
	interface RegistrationPageUiBinder extends UiBinder<AbsolutePanel, RegistrationPage> {}

	private static RegistrationPageUiBinder ourUiBinder = GWT.create(RegistrationPageUiBinder.class);
	private Widget widget = ourUiBinder.createAndBindUi(this);
	
	@UiField
	Button btnSignUp;
	@UiField
	Button btnSignIn;
	@UiField
	TextBox tbPassword;
	@UiField
	TextBox tbEmail;
	@UiField
	TextBox tbName;
	
	public RegistrationPage(){
		initWidget(widget);
		
		
		btnSignUp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RegistrationUserRequest request = new RegistrationUserRequest(tbName.getText(), tbEmail.getText(), tbPassword.getText());
				String jsonBody = request.getJson();
				sendSignUpRest(jsonBody);
			}
		});
		
		btnSignIn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(new LoginPage());
			}
		});
	}
	
	 private void sendSignUpRest(final String jsonString) {
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL_SIGN_UP);
		 
		 try {
			 builder.setHeader(CONTENT_TYPE, "application/json");
			 builder.setRequestData(jsonString);
			 builder.setCallback( new RequestCallback() {
				 public void onError(Request request, Throwable exception) {
					 Window.alert(exception.getMessage());
				 }
				 public void onResponseReceived(Request request, Response response) {
					 JSONObject jobj = JSONParser.parseStrict(response.getText()).isObject();
					 UserDto user = UserDto.fromJson(jobj);
					 if (201 == response.getStatusCode())
					 {
						 Window.alert("Пользователь " + user.getName() + " зарегистрирован. \nМожете авторизоваться.");
						RootPanel.get().clear();
						RootPanel.get().add(new LoginPage());
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

