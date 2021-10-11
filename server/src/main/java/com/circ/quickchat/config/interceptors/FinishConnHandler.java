package com.circ.quickchat.config.interceptors;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.Alerts.UserAlert;

public class FinishConnHandler extends WebSocketHandlerDecorator {

	public FinishConnHandler(WebSocketHandler delegate) {
		super(delegate);
	}

	@Autowired
	private Map<String, User> sessionKeyToUser;
	
	@Autowired
	private UserAlert userAlert;
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		String sessionId = (String) session.getAttributes().get("sessionId").toString();
		User user = sessionKeyToUser.get(sessionId);
		sessionKeyToUser.remove(sessionId);
		userAlert.disconect(user);
	}

}
