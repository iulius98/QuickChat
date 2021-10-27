package com.circ.quickchat.controller;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.Group;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.GroupService;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.WebsocketMessage;

import DTO.SimpleGroupDTO;
import constant.MessageType;

@RestController
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
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
		groupService.sendMessage(message, sessionId);
	}
	
	@PostMapping("/chat/create/{sessionId}")
	public SimpleGroupDTO createNewGroup(@RequestBody  Group group, @PathVariable String sessionId) {
		User userThatCreatedChat = userService.getUserBySessionId(sessionId);
		List<Long> usersThatWillBeAddedInChat = group.getChat().getUsers().stream().map(usr -> usr.getId())
				.collect(Collectors.toList());
		Group groupFromDb = userService.addUsersInChat(group, usersThatWillBeAddedInChat);
		groupFromDb.getChat().getUsers().add(userThatCreatedChat);
//		userUtilCommun.sendToUser(sessionId, WebsocketMessage.builder().messageType(MessageType.REQUESTED_CHAT)
//				.content(chatFromDb.toChatDTO()).build());
		return groupService.save(groupFromDb).toSimpleGroupDTO();
	}
	
	@MessageMapping("/chat/addUser/{chatId}/{userId}")
	public void addUserInChat(@DestinationVariable Long chatId, @DestinationVariable Long userId,
			SimpMessageHeaderAccessor  headerAccessor) {
		Group group = groupService.getGroupById(chatId);
		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		User user = userService.getUserBySessionId(sessionId);
		if (!group.getChat().getUsers().stream().anyMatch(usr -> usr.getId().equals(user.getId()))) {
			throw new InternalError("User " + user.getId() + " that try to add another user isn't into chat!");
		}
		userService.addUserInChat(group, userId);
	}
	
	@MessageMapping("user/{sessionId}/chat/{groupId}")
	@Transactional
	public void getGroup(@DestinationVariable String sessionId, @DestinationVariable Long groupId) {
		Group group = groupService.getGroupById(groupId);
		User user = userService.getUserBySessionId(sessionId);
		user.setCurrentChat(group.getChat());
		userService.save(user);
		userUtilCommun.sendToUser(sessionId, WebsocketMessage.builder().messageType(MessageType.REQUESTED_CHAT)
				.content(group.toGroupDTO()).build());
	}
	
}
