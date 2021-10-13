package com.circ.quickchat.utils.Alerts;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.ActionUserMessage;
import com.circ.quickchat.entity.UserListMessage;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.communcation.UserUtilCommun;

import constant.MessageType;

@Component
public class UserAlert {
	
	@Autowired
	private Map<String, User> sessionKeyToUser;
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private UserService userService;
	
	public void connectNewUser(User user) {
		actionOnUser(user, MessageType.ADD_USER);
	}
	
	public void disconect(User user) {
		actionOnUser(user, MessageType.DELETE_USER);
	}
	
	public void updateUser(User user) {
		actionOnUser(user, MessageType.UPDATE_USER);
	}
	
	
	public void sendUserListTo(String sessionId) {
		List<User> userList = sessionKeyToUser.values().stream()
				.collect(Collectors.toList());
		UserListMessage message = UserListMessage.builder()
				.content(userList)
				.messageType(MessageType.UPGRADE_LIST_USERS).build();
		userUtilCommun.sendToUser(sessionId, message);
	}
	
	private void actionOnUser(User user, MessageType actionType) {
		ActionUserMessage message = ActionUserMessage.builder().content(user)
				.messageType(actionType).build();
		userUtilCommun.sendToAllUsers(message);
	}
}
