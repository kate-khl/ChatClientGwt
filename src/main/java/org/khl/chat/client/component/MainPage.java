package org.khl.chat.client.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabBar.Tab;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainPage extends Composite {
	
	interface MainPageUiBinder extends UiBinder<AbsolutePanel, MainPage> {}

	private static MainPageUiBinder ourUiBinder = GWT.create(MainPageUiBinder.class);
	private Widget widget = ourUiBinder.createAndBindUi(this);
	
	
	@UiField
	TabLayoutPanel tabPanel;
	 
	public MainPage(){
		initWidget(widget);
	
		tabPanel.setHeight("300px");
		
		tabPanel.add(new ChatPage(), "Чаты");
		tabPanel.add(new Grid(), "Пользователи");
		
		tabPanel.selectTab(0);
	 }
}

