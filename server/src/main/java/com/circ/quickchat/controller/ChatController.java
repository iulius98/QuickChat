package com.circ.quickchat.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
	
	/*
	 * @Autowired private Map<String, User> sessionKeyToUser;
	 */
	
	
	@Autowired
	private Map<String, Chat> chats;
	
	@Autowired
	private Map<String, User> sessionKeyToUser;
	
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
		User userThatCreatedChat = sessionKeyToUser.get(sessionId);
		HashSet<User> usersFromChat = new HashSet<User>();
		usersFromChat.add(userThatCreatedChat);
		Chat chat = Chat.builder().id(UUID.randomUUID().toString()).users(usersFromChat).build();
		chats.put(chat.getId(), chat);
		return chat;
	}
	
	@MessageMapping("/chat/addUser/{chatId}/{userId}")
	public void addUserInChat(@PathVariable String chatId, @PathVariable String userId) {
		Chat chat = chats.get(chatId);
		userService.addUserInChat(chat, userId);
	}
	
	@GetMapping("user/{sessionId}/chat/{chatId}")
	public Chat getChat(@PathVariable String sessionId, @PathVariable String chatId) {
		Chat requestChat = chats.get(chatId);
		if (requestChat != null) {
			sessionKeyToUser.get(sessionId).setCurrentChatId(chatId);
			return requestChat;
		} else {
			throw new InternalError("Bad chatId");
		}
	}
}
