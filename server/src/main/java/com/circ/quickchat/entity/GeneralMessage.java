package com.circ.quickchat.entity;

import constant.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GeneralMessage<T> {
	protected T content;
	protected MessageType messageType;
	
	public GeneralMessage() {
	}
}
