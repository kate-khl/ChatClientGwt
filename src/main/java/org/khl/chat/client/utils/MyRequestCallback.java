package org.khl.chat.client.utils;

import org.khl.chat.client.dto.AppData;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public interface MyRequestCallback extends RequestCallback{

	@Override
	public default void onResponseReceived(Request request, Response response) {
	
		if(response.getStatusCode() >=200 && response.getStatusCode() < 300)
			onOk(request, response);
		else if (response.getStatusCode() == 401 || response.getStatusCode() == 404)
			onUnuthorizen(request, response);
		}

	
	public void onOk(Request request, Response response);
	
	public default void onUnuthorizen(Request request, Response response) {
		AppData.INSTANCE.logout();
	}

}
