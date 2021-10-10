package com.circ.quickchat.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;

@RestController
public class MessageController {
	
	@Autowired
	private Map<String, User> sessionKeyToUser; 
	
	@Autowired
	public SimpMessageSendingOperations send;

	//endpoint for websocket client
	@MessageMapping("/chat")
	public void processMessage(Message message,  SimpMessageHeaderAccessor  headerAccessor) {
		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		message.setId(UUID.randomUUID().toString());
		sendToUsers(message);
	}
	
	private void sendToUsers(Message message) {
		String senderId = message.getAuthor();
		message.setAuthor(sessionKeyToUser.get(senderId).getName());
		sessionKeyToUser.keySet().stream().forEach(key -> {
			if (!key.equals(senderId)) {
				send.convertAndSendToUser(senderId, "/usertell", message);
			}
		});
	}
}
