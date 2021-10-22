package com.circ.quickchat.config.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.Alerts.ChatAllert;
import com.circ.quickchat.utils.Alerts.UserAlert;

import constant.ChatConstants;

public class ConnHandler extends WebSocketHandlerDecorator {

	public ConnHandler(WebSocketHandler delegate) {
		super(delegate);
	}

	@Autowired
	private Map<String, User> sessionKeyToUser;
	
	@Autowired
	private UserAlert userAlert;
	
	@Autowired
	private ChatAllert chatAllert;
	
	@Autowired
	private Map<String, Chat> chats;
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		String sessionId = (String) session.getAttributes().get("sessionId").toString();
		User user = sessionKeyToUser.get(sessionId);
		sessionKeyToUser.remove(sessionId);
		userAlert.disconect(user);
		List<String> removedChats = new ArrayList<>();
		chats.entrySet().stream().filter(entrySet -> entrySet.getValue().getUsers().contains(user))
			.forEach(entrySet -> {
				Chat chatForUser = entrySet.getValue();
				chatForUser.getUsers().remove(user);
				if (chatForUser.getUsers().isEmpty() && !chatForUser.getId().equals(ChatConstants.principalChatId)) {
					removedChats.add(entrySet.getKey());
				} else {
					chatAllert.deleteUserInChat(chatForUser, user);
				}
			});
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
//		String sessionId = (String) session.getAttributes().get("sessionId");
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
////				userAlert.sendUserListTo(sessionId);
////				chatAllert.sendChatToUser(chats.get(ChatConstants.principalChatId), sessionId);
//			}
//		}).start();
	}
}
