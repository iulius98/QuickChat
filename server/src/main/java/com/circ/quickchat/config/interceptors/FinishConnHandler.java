package com.circ.quickchat.config.interceptors;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.Alerts.UserAlert;

public class FinishConnHandler extends WebSocketHandlerDecorator {

	public FinishConnHandler(WebSocketHandler delegate) {
		super(delegate);
	}

	@Autowired
	Map<String, User> sessionKeyToUser;
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		String sessionId = (String) session.getAttributes().get("sessionId").toString();
		User user = sessionKeyToUser.get(sessionId);
		sessionKeyToUser.remove(sessionId);
		UserAlert.userDisconect(user);
	}

}
