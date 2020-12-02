package org.khl.chat.client.dto;

import java.util.Collection;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.view.client.ProvidesKey;

public class ChatDto {

	private Long id;
	private String name;
	private UserDto author;
	
	
	
    public ChatDto(Long id, String name, UserDto author) {
		this.id = id;
		this.name = name;
		this.author = author;
	}

	public static final ProvidesKey<ChatDto> KEY_PROVIDER = new ProvidesKey<ChatDto>() {
        @Override
        public Object getKey(ChatDto item) {
          return item == null ? null : item.getId();
        }
      };
      
  	public static ChatDto fromJson(JSONObject jobj) {
		Long id = (long) jobj.get("id").isNumber().doubleValue();
		String name = jobj.get("name").isString().stringValue();
		UserDto author = UserDto.fromJson(jobj.get("author").isObject());

		return new ChatDto(id, name, author);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(UserDto author) {
		this.author = author;
	}
	
	
	
}
