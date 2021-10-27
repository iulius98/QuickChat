package com.circ.quickchat.utils.Alerts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.Group;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.GroupService;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.UserAndChat;
import com.circ.quickchat.websocket.WebsocketMessage;

import constant.MessageType;

@Component
public class UserAllert {
	
	
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private GroupService groupService;
	
	public void updateUser(User user) {
		List<Group> groups = groupService.getChatThatContainsUser(user);
		List<String> listUserSessionsId = groups.stream().flatMap(group -> {
			Stream<String> usersThatUseCurrentChatNow = group.getChat().getUsers().stream()
				.filter(userC -> userC.getCurrentChat() != null && userC.getCurrentChat().equals(group.getChat()))
				.map(userC -> userC.getSessionId());
			return usersThatUseCurrentChatNow;
		}).collect(Collectors.toList());
		userUtilCommun.sendToUsers(WebsocketMessage.builder()
				.messageType(MessageType.UPDATE_CHAT_USER).content(user.toUserDTO()).build(), listUserSessionsId);
	}
}
