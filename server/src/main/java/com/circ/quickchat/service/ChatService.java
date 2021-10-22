package com.circ.quickchat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.utils.communcation.UserUtilCommun;

@Service
public class ChatService {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private Map<String, Chat> chats;
	
	public void sendMessage(Message message, String sessionIdAuthor) {
		Chat chat = chats.get(message.getChatId());
		chat.getMessages().add(message);
		List<String> usersFromChatWithoutAuthor = new ArrayList<String>();
		chat.getUsers().stream().filter(usr -> usr.getCurrentChatId() != null &&
				usr.getCurrentChatId().equals(chat.getId())).forEach(user -> {
			if(!user.getSessionId().equals(sessionIdAuthor)) {
				usersFromChatWithoutAuthor.add(user.getSessionId());
			}
		});
		userUtilCommun.sendToUsers(message, usersFromChatWithoutAuthor);
	}
}
