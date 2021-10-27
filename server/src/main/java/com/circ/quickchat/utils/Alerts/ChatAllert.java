package com.circ.quickchat.utils.Alerts;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.Group;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.UserAndChat;
import com.circ.quickchat.websocket.WebsocketMessage;

import constant.MessageType;

@Component
public class ChatAllert {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	public void addUserInChat(Group group, User user) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder().content(UserAndChat.builder()
				.user(user.toUserDTO()).chatId(group.getId()).build()).messageType(MessageType.ADD_USER_CHAT).build();
		
		userUtilCommun.sendToUsers(websocketMessage, group.getChat().getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(group.getChat())).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
		
		group.getChat().getUsers().add(user);
		sendChatToUser(group.toSimpleGroupDTO(), user.getSessionId());
		
	}
	
	public void addUserInChatV2(Group group, User user) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder().content(UserAndChat.builder()
				.user(user.toUserDTO()).chatId(group.getId()).build()).messageType(MessageType.ADD_USER_CHAT).build();
		
		userUtilCommun.sendToUsers(websocketMessage, group.getChat().getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(group.getChat())).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
		
		sendChatToUser(group.toSimpleGroupDTO(), user.getSessionId());
		
	}
	
	public void deleteUserInChat(Group group, User user) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder()
				.content(UserAndChat.builder().chatId(group.getId()).user(user.toUserDTO()).build())
				.messageType(MessageType.DELETE_USER_CHAT).build();
		userUtilCommun.sendToUsers(websocketMessage, group.getChat().getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(group.getChat())).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
	}
	
	public void sendChatToUser(Object chat, String sessionId) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder()
				.content(chat).messageType(MessageType.NEW_CHAT).build();
		userUtilCommun.sendToUser(sessionId, websocketMessage);
	}
}
