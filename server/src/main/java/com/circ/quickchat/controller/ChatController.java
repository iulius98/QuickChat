package com.circ.quickchat.controller;

import java.util.ArrayList;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.ChatService;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.WebsocketMessage;

import DTO.ChatDTO;
import constant.MessageType;

@RestController
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	

	//endpoint for websocket client
	@MessageMapping("/chat")
	public void processMessage(Message message,  SimpMessageHeaderAccessor  headerAccessor) {
		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		User user = userService.getUserBySessionId(sessionId);
		message = message.toBuilder().authorId(user.getId()).authorName(user.getName()).build();
		chatService.sendMessage(message, sessionId);
	}
	
	@PostMapping("/chat/create/{sessionId}")
	public ChatDTO createNewChat(@PathVariable String sessionId, @RequestParam String name) {
		Chat chat = Chat.builder().users(new HashSet<User>())
				.name(name).build();
		User userThatCreatedChat = userService.getUserBySessionId(sessionId);
		Chat chatFromDb = chatService.save(chat);	
		chatFromDb.getUsers().add(userThatCreatedChat);
//		userUtilCommun.sendToUser(sessionId, WebsocketMessage.builder().messageType(MessageType.REQUESTED_CHAT)
//				.content(chatFromDb.toChatDTO()).build());
		return chatService.save(chatFromDb).toChatDTO();
	}
	
	@MessageMapping("/chat/addUser/{chatId}/{userId}")
	public void addUserInChat(@DestinationVariable Long chatId, @DestinationVariable Long userId,
			SimpMessageHeaderAccessor  headerAccessor) {
		Chat chat = chatService.getChatById(chatId);
		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		User user = userService.getUserBySessionId(sessionId);
		if (!chat.getUsers().stream().anyMatch(usr -> usr.getId().equals(user.getId()))) {
			throw new InternalError("User " + user.getId() + " that try to add another user isn't into chat!");
		}
		userService.addUserInChat(chat, userId);
	}
	
	@MessageMapping("user/{sessionId}/chat/{chatId}")
	@Transactional
	public void getChat(@DestinationVariable String sessionId, @DestinationVariable Long chatId) {
		Chat chat = chatService.getChatById(chatId);
		User user = userService.getUserBySessionId(sessionId);
		user.setCurrentChat(chat);
		userService.save(user);
		userUtilCommun.sendToUser(sessionId, WebsocketMessage.builder().messageType(MessageType.REQUESTED_CHAT)
				.content(chat).build());
	}
	
}
