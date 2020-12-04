package org.khl.chat.client.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainPage extends Composite {
	
	interface MainPageUiBinder extends UiBinder<AbsolutePanel, MainPage> {}

	private static MainPageUiBinder ourUiBinder = GWT.create(MainPageUiBinder.class);
	private Widget widget = ourUiBinder.createAndBindUi(this);
	
	@UiField
	TabLayoutPanel tabPanel;
	 
	public MainPage(){
		initWidget(widget);
	
		tabPanel.setHeight("500px");
		
		tabPanel.add(new Grid(), "Пользователи");
		tabPanel.add(new ChatPage(), "Чаты");
		
		tabPanel.selectTab(0);
	 }
}

