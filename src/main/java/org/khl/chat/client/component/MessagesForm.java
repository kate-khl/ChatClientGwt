package org.khl.chat.client.component;

import java.util.ArrayList;

import org.khl.chat.client.dto.AppData;
import org.khl.chat.client.dto.ChatDto;
import org.khl.chat.client.dto.MessageDto;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class MessagesForm extends Composite{
	
	private static final String URL_GET_MESSAGES = "http://127.0.0.1:8080/chats/{chatId}/messages?page= {page}1&size= {size}";
	ListDataProvider<MessageDto> dataProvider = new ListDataProvider<>();
	private ArrayList<MessageDto> messages = new ArrayList<>();
	
	interface ChatPageUiBinder extends UiBinder<HTMLPanel, MessagesForm> {}
	
	private static ChatPageUiBinder ourUiBinder = GWT.create(ChatPageUiBinder.class);
	private Widget widget = ourUiBinder.createAndBindUi(this);
	
	@UiField
	ShowMorePagerPanel pagerPanel;
	
	@UiField
	RangeLabelPager rangeLabelPager;
	
	private CellList<MessageDto> cellList;
	
	static class MessageCell extends AbstractCell<MessageDto> {
		
		@Override
		public void render(Context context, MessageDto value, SafeHtmlBuilder sb) {
			if (value == null) {
				return;
			}
			
			sb.appendHtmlConstant("<table>");
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getValue());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getDate().toString() + "\n" + value.getAuthor().getName());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}
	
	public MessagesForm() {}
	
	public MessagesForm(ChatDto chat) {
		
		initWidget(widget);
		initMsgForm(chat.getId());

	}
	
	public void initMsgForm(Long chatId){
		initWidget(widget);
		
		getMessagesList(chatId);
		
		MessageCell cell = new MessageCell();
		
		cellList = new CellList<MessageDto>(cell, MessageDto.KEY_PROVIDER);
		cellList.setPageSize(30);
		cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
		final SingleSelectionModel<MessageDto> selectionModel = new SingleSelectionModel<MessageDto>(MessageDto.KEY_PROVIDER);
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				
			}
		});
		
		dataProvider.addDataDisplay(cellList);
		pagerPanel.setDisplay(cellList);
		rangeLabelPager.setDisplay(cellList);	
	}
	
	private void getMessagesList(Long chatId) {
		String url = URL_GET_MESSAGES.replace("{chatId}", chatId.toString());
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setHeader("Authorization", AppData.INSTANCE.getToken());
		try {
			builder.setCallback( new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert(exception.getMessage());
				}
				public void onResponseReceived(Request request, Response response) {
					
					if (201 == response.getStatusCode()){
						JSONValue jval = JSONParser.parseStrict(response.getText());
						JSONArray jarray = jval.isArray();
						messages = jsonArrayToList(jarray);
						dataProvider.setList(messages);
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
	
	private ArrayList<MessageDto> jsonArrayToList(JSONArray jarray) {
		ArrayList<MessageDto> list = new ArrayList<>();
		int lenght = jarray.size();
		for (int i = 0; i < lenght; i++) {
			list.add(MessageDto.fromJson(jarray.get(i).isObject()));
		}
		return list;
	}

}
