package com.circ.quickchat.utils.Alerts;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.UserAndChat;
import com.circ.quickchat.websocket.WebsocketMessage;

import constant.MessageType;

@Component
public class ChatAllert {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	public void addUserInChat(Chat chat, User user) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder().content(UserAndChat.builder()
				.user(user.toUserDTO()).chatId(chat.getId()).build()).messageType(MessageType.ADD_USER_CHAT).build();
		
		userUtilCommun.sendToUsers(websocketMessage, chat.getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(chat)).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
		
		chat.getUsers().add(user);
		sendChatToUser(chat.toChatDTO(), user.getSessionId());
		
	}
	
	public void addUserInChatV2(Chat chat, User user) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder().content(UserAndChat.builder()
				.user(user.toUserDTO()).chatId(chat.getId()).build()).messageType(MessageType.ADD_USER_CHAT).build();
		
		userUtilCommun.sendToUsers(websocketMessage, chat.getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(chat)).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
		
		sendChatToUser(chat.toChatDTO(), user.getSessionId());
		
	}
	
	public void deleteUserInChat(Chat chat, User user) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder()
				.content(UserAndChat.builder().chatId(chat.getId()).user(user.toUserDTO()).build())
				.messageType(MessageType.DELETE_USER_CHAT).build();
		userUtilCommun.sendToUsers(websocketMessage, chat.getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(chat)).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
	}
	
	public void sendChatToUser(Object chat, String sessionId) {
		WebsocketMessage websocketMessage = WebsocketMessage.builder()
				.content(chat).messageType(MessageType.NEW_CHAT).build();
		userUtilCommun.sendToUser(sessionId, websocketMessage);
	}
}
