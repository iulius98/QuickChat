package com.circ.quickchat.utils.Alerts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.ActionChatMessage;
import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.ChatMessage;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.communcation.UserUtilCommun;

import constant.MessageType;

@Component
public class ChatAllert {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	public void addUserInChat(Chat chat, User user) {
		ActionChatMessage message = ActionChatMessage.builder().content(chat.getId()).user(user)
				.messageType(MessageType.ADD_USER_CHAT).build();
		userUtilCommun.sendToUsers(message, chat.getUsers().stream().filter(usr -> usr.getCurrentChatId() != null &&
						usr.getCurrentChatId().equals(chat.getId())).map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
		chat.getUsers().add(user);
		sendChatToUser(chat, user.getSessionId());
		
	}
	
	public void deleteUserInChat(Chat chat, User user) {
		ActionChatMessage message = ActionChatMessage.builder().content(chat.getId()).user(user)
				.messageType(MessageType.DELETE_USER_CHAT).build();
		userUtilCommun.sendToUsers(message, chat.getUsers().stream().map(usr -> usr.getSessionId())
				.collect(Collectors.toList()));
	}
	
	public void sendChatToUser(Chat chat, String sessionId) {
		Map<String, String> newChatContent = new HashMap<>();
		newChatContent.put("chatId", chat.getId());
		newChatContent.put("chatName", chat.getName());
		ChatMessage message = ChatMessage.builder().content(newChatContent).messageType(MessageType.NEW_CHAT).build();
		userUtilCommun.sendToUser(sessionId, message);
	}
}
