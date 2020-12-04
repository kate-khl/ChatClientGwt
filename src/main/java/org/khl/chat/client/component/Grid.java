package org.khl.chat.client.component;

import java.util.ArrayList;
import java.util.List;

import org.khl.chat.client.dto.AppData;
import org.khl.chat.client.dto.UserDto;
import org.khl.chat.client.utils.MyRequestCallback;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

public class Grid extends Composite{
	
	
	interface GridUiBinder extends UiBinder<DockLayoutPanel, Grid> {}
	private static GridUiBinder ourUiBinder = GWT.create(GridUiBinder.class);
	
	private static final String URL_GET_USERS = "http://127.0.0.1:8080/users/list";
	
	private ArrayList<UserDto> users = new ArrayList<UserDto>();
	ListDataProvider<UserDto> dataProvider = new ListDataProvider<>();
	
	@UiField(provided = true)
	DataGrid<UserDto> dataGrid;
	
	@UiField(provided = true)
	SimplePager pager;
	
	public Grid() {
		
		getUsersList();
		
		dataGrid = new DataGrid<>(UserDto.KEY_PROVIDER);
		
		dataGrid.setAutoHeaderRefreshDisabled(true);
		dataGrid.setEmptyTableWidget(new Label("List is Empty"));
		
		ListHandler<UserDto> sortHandler = new ListHandler<UserDto>(users);
		dataGrid.addColumnSortHandler(sortHandler);
	    final SelectionModel<UserDto> selectionModel = new MultiSelectionModel<UserDto>(UserDto.KEY_PROVIDER);
	    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());
	
	    dataProvider.addDataDisplay(dataGrid);
	    
	    initColumns();
	    initPager();

	    initWidget(ourUiBinder.createAndBindUi(this));
	}

	private void initPager() {
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.LEFT, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
	}

	private void initColumns() {
		//Name
		Column<UserDto, String> nameColumn = new Column<UserDto, String>(new TextCell()) {
			@Override
			public String getValue(UserDto object) {
				return object.getName();
			}
		};
		dataGrid.addColumn(nameColumn, "Name");

		//Email
		Column<UserDto, String> emailColumn = new Column<UserDto, String>(new TextCell()) {
			@Override
			public String getValue(UserDto object) {
				return object.getEmail();
			}
		};
		dataGrid.addColumn(emailColumn, "Email");
	}
	
	private void getUsersList() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL_GET_USERS);
		builder.setHeader("Authorization", AppData.INSTANCE.getToken());
		try {
			builder.setCallback( new MyRequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert(exception.getMessage());
				}
				public void onOk(Request request, Response response) {
					
					if (200 == response.getStatusCode()){
						String ss = response.getText();
						JSONValue jval = JSONParser.parseStrict(response.getText());
						JSONArray jarray = jval.isArray();
						users = jsonArrayToList(jarray);
						dataProvider.setList(users);
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
	
	private ArrayList<UserDto> jsonArrayToList(JSONArray jarray) {
		ArrayList<UserDto> list = new ArrayList<UserDto>();
		int lenght = jarray.size();
		for (int i = 0; i < lenght; i++) {
			list.add(UserDto.fromJson(jarray.get(i).isObject()));
		}
		return list;
	}
}
