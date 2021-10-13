package com.circ.quickchat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import constant.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Message extends GeneralMessage<String> {
	
	private String id;
	private String chatId;
	private String author;
	private Long timestamp;
	
	public Message() {
		super.messageType = MessageType.MESSAGE;
	}
}
