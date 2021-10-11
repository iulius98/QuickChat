package com.circ.quickchat.utils.Alerts;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.UpgradeListUsersMessage;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.communcation.UserUtilCommun;

import constant.MessageType;

@Component
public class UserAlert {
	
	@Autowired
	private Map<String, User> sessionKeyToUser;
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	public void connectNewUser(User newUser) {
		changeStatusUserList(true, newUser);
	}
	
	public void disconect(User user) {
		changeStatusUserList(false, user);
	}
	
	private void changeStatusUserList(boolean isCome, User changedUser) {
		List<User> newUserList = sessionKeyToUser.values().stream()
				.map(user -> User.builder().name(user.getName()).id(UUID.randomUUID().toString()).build())
				.collect(Collectors.toList());
		UpgradeListUsersMessage message = UpgradeListUsersMessage.builder()
				.content(newUserList).changedUser(changedUser.getName())
				.isCome(isCome).messageType(MessageType.UPGRADE_LIST_USERS).build();
		userUtilCommun.sendToAllUsers(message);
	}
}
