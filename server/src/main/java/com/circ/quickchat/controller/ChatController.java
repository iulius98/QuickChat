package com.circ.quickchat.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.ChatService;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.communcation.UserUtilCommun;

@RestController
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	

	//endpoint for websocket client
	@MessageMapping("/chat")
	public void processMessage(Message message,  SimpMessageHeaderAccessor  headerAccessor) {
		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		chatService.sendMessage(message, sessionId);
	}
	
	@PostMapping("/chat/create/{sessionId}")
	public Chat createNewChat(@PathVariable String sessionId) {
		Chat chat = Chat.builder().users(new HashSet<User>()).build();
		User userThatCreatedChat = userService.getUserBySessionId(sessionId);
		userThatCreatedChat.setCurrentChat(chat);
		chat.getUsers().add(userService.save(userThatCreatedChat));
		Chat chatFromDb = chatService.save(chat);
		return chatFromDb;
	}
	
	@MessageMapping("/chat/addUser/{chatId}/{userId}")
	public void addUserInChat(@DestinationVariable Long chatId, @DestinationVariable Long userId) {
		Chat chat = chatService.getChatById(chatId);
		userService.addUserInChat(chat, userId);
	}
	
	@GetMapping("user/{sessionId}/chat/{chatId}")
	public Chat getChat(@PathVariable String sessionId, @PathVariable Long chatId) {
		Chat chat = chatService.getChatById(chatId);
		User user = userService.getUserBySessionId(sessionId);
		user.setCurrentChat(chat);
		userService.save(user);
		return chat;
	}
	
}
