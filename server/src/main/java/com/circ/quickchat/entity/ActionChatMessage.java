package com.circ.quickchat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class ActionChatMessage extends GeneralMessage<Chat> {
	private User user;
}
