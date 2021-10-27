package com.circ.quickchat.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.circ.quickchat.utils.Alerts.ChatAllert;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.WebsocketMessage;
import constant.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Conversation;
import com.circ.quickchat.entity.ConversationInfo;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.ConversationService;
import com.circ.quickchat.service.UserService;

import DTO.ConversationDTO;
import DTO.SimpleConversationDTO;

import javax.transaction.Transactional;

@RestController
public class ConversationController {
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired 
	private UserService userService;

	@Autowired
	private UserUtilCommun userUtilCommun;

	@Autowired
	private ChatAllert chatAllert;
	
	@PostMapping("/conversations/create/{sessionId}/{anotherUserId}")
	public SimpleConversationDTO createConversation(@RequestBody ConversationInfo conversationInfo, 
			@PathVariable String sessionId, @PathVariable Long anotherUserId) {
		Set<User> userSet = new HashSet<User>();
		List<ConversationInfo> info = new ArrayList<ConversationInfo>();
		User userThatCreatedConv = userService.getUserBySessionId(sessionId);
		conversationInfo.setUserId(userThatCreatedConv.getId());
		info.add(conversationInfo);
		info.add(ConversationInfo.builder().name(userThatCreatedConv.getName()).userId(anotherUserId).build());
		userSet.add(userThatCreatedConv);
		User anotherUser = userService.getUserForId(anotherUserId);
		userSet.add(anotherUser);
		Chat newChat = Chat.builder().users(userSet).messages(new ArrayList<Message>()).build();
		Conversation conversation = conversationService.save(Conversation.builder().chat(newChat)
				.conversationsInfo(info).build());
		chatAllert.addUserInConversation(conversation, anotherUser);
		return conversation.toSimpleConversationDTO(userThatCreatedConv.getId());
	}
	
	@MessageMapping("/conversations/get/{convId}/user/{sessionId}")
	@Transactional
	public void getConversation(@DestinationVariable Long convId, @DestinationVariable String sessionId) {
		Conversation conversation =  conversationService.findById(convId);
		User currentUser = userService.getUserBySessionId(sessionId);
		currentUser.setCurrentChat(conversation.getChat());
		userService.save(currentUser);
		userUtilCommun.sendToUser(sessionId, WebsocketMessage.builder().messageType(MessageType.REQUESTED_CHAT)
				.content(conversation.toConversationDTO(currentUser.getId())).build());
	}
	
}
