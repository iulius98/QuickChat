package com.circ.quickchat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import DTO.UserDTO;
import constant.ChatConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	
	private String id;
	
	@JsonIgnore(value = true)
	private String sessionId;
	
	private String name;
	
	private long timestamp;
	
	@JsonIgnore(value = true)
	private boolean isConnect = false;
	
	@JsonIgnore(value = true)
	private String currentChatId;
	
	public User() {
	}
	
	public UserDTO toUserDTO() {
		return UserDTO.builder().id(this.id)
				.sessionId(this.sessionId)
				.name(this.name)
				.timestamp(timestamp).build();
	}
	
	
	
}
