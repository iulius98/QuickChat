package com.circ.quickchat.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.ChatRepository;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.WebsocketMessage;

import constant.MessageType;

@RestController
@RequestMapping("/writer")
public class WriterController {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserService userService;
	
	@MessageMapping("/writing")
	public void currentlyWriting(SimpMessageHeaderAccessor  headerAccessor) {
		alertUserWriteAction(headerAccessor, MessageType.USER_IS_WRITING);
	}
	
	
	@MessageMapping("/stopped-writing")
	public void stoppedWriting(SimpMessageHeaderAccessor  headerAccessor) {
		alertUserWriteAction(headerAccessor, MessageType.USER_STOPPED_WRITING);
	}
	
	private void alertUserWriteAction(SimpMessageHeaderAccessor headerAccessor, MessageType messageType) {
		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		User userThatWrite = userService.getUserBySessionId(sessionId);
		Long chatId = userThatWrite.getCurrentChat().getId();
		Chat chat = chatRepository.findById(chatId)
				.orElseThrow(() -> new InternalError("Chat with id: " + chatId +
						" doesn't exist"));
		WebsocketMessage messageWebsocketsMessage = WebsocketMessage.builder().messageType(messageType)
				.content(userThatWrite.toUserDTO()).build();
		userUtilCommun.sendToUsers(messageWebsocketsMessage, chat.getUsers()
				.stream().filter(usr -> !usr.getSessionId().equals(sessionId) && usr.getCurrentChat() != null
						&& usr.getCurrentChat().equals(chat)).map(User::getSessionId).collect(Collectors.toList()));
	}

}
