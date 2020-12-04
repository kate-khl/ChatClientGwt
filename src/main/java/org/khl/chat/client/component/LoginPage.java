package org.khl.chat.client.component;

import org.khl.chat.client.LoginService;
import org.khl.chat.client.LoginServiceAsync;
import org.khl.chat.client.dto.AppData;
import org.khl.chat.client.dto.LoginRequestDto;
import org.khl.chat.client.utils.MyRequestCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginPage extends Composite {
	
	private static final String URL_SIGN_IN = "http://127.0.0.1:8080/auth";
	private static final String URL_SIGN_UP = "http://127.0.0.1:8080/registration";
	private static final String CONTENT_TYPE = "Content-Type";
	private AppData appData = AppData.INSTANCE;
	
	interface LoginPageUiBinder extends UiBinder<AbsolutePanel, LoginPage> {}

	private static LoginPageUiBinder ourUiBinder = GWT.create(LoginPageUiBinder.class);
	private Widget widget = ourUiBinder.createAndBindUi(this);
	private static LoginServiceAsync loginService = GWT.create(LoginService.class); 
	
	
	@UiField
	Button btnSignIn;
	@UiField
	Button btnSignUp;
	@UiField
	PasswordTextBox tbPassword;
	@UiField
	TextBox tbEmail;
//	@UiField
//	Grid dataGrid;
	
	public LoginPage(){
		initWidget(widget);

//		dataGrid = new Grid(null);
		
		btnSignIn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoginRequestDto loginRequest = new LoginRequestDto(tbEmail.getText(), tbPassword.getText());
				String jsonBody = loginRequest.getJson();
				GWT.debugger();
				sendSignInRest(jsonBody);
			}
		});
		
		btnSignUp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(new RegistrationPage());
			}
		});
	}
	
	 private void sendSignInRest(final String jsonString) {
		 GWT.debugger();
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL_SIGN_IN);

        try {
            builder.setHeader(CONTENT_TYPE, "application/json");
            builder.setRequestData(jsonString);
            builder.setCallback( new MyRequestCallback() {
                public void onError(Request request, Throwable exception) {
                    Window.alert(exception.getMessage());
                }

				@Override
				public void onOk(Request request, Response response) {
                	GWT.debugger();
                    if (200 == response.getStatusCode()){
	                	String responseJson = response.getText();
	                	appData.setFieldsFromJson(responseJson);
	                	loginService.login(appData.getToken(), new AsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								RootPanel.get().clear();
								RootPanel.get().add(new MainPage());
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
                    } 
                    else {
                        Window.alert("Что-то пошло не так: " + response.getStatusText());
                    }
				}
            });
            builder.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
	 }
}

