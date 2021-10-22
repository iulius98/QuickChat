package com.circ.quickchat.websocket;

import constant.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebsocketMessage {
	protected Object content;
	protected MessageType messageType;
}
