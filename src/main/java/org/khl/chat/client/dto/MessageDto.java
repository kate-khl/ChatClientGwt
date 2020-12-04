package org.khl.chat.client.dto;

import java.util.Date;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.view.client.ProvidesKey;

public class MessageDto {
	
	private Long id;
	private String value;
	private UserDto author;
	private String date;
	
	public static final ProvidesKey<MessageDto> KEY_PROVIDER = new ProvidesKey<MessageDto>() {
        @Override
        public Object getKey(MessageDto item) {
          return item == null ? null : item.getId();
        }
      };

	public MessageDto() {}
	
	public MessageDto(Long id, String value, UserDto author, String date) {
		super();
		this.id = id;
		this.value = value;
		this.author = author;
		this.date = date;
	}
	
  	public static MessageDto fromJson(JSONObject jobj) {
  		
		Long id = (long) jobj.get("id").isNumber().doubleValue();
		String value = jobj.get("value").isString().stringValue();
		UserDto author = UserDto.fromJson(jobj.get("author").isObject());
		String date = jobj.get("date").isString().toString();

		return new MessageDto(id, value, author, date);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public UserDto getAuthor() {
		return author;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date.toString();
	}
	
}
