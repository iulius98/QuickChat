package com.circ.quickchat.websocket;

import constant.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class WebsocketMessage {
	protected Object content;
	protected MessageType messageType;
}
