package com.circ.quickchat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.ChatRepository;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.WebsocketMessage;

import constant.MessageType;

@Service
public class ChatService {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private ChatRepository chatRepository;
	
	public void sendMessage(Message message, String sessionIdAuthor) {
		Chat chat = chatRepository.findById(message.getChat().getId()).orElseThrow(() -> new InternalError(
				"It doesn't exist a chat with id: " + message.getId()));
		chat.getMessages().add(message);
		chatRepository.save(chat);
		List<String> usersFromChatWithoutAuthor = new ArrayList<String>();
		chat.getUsers().forEach(user -> {
			if(user.getCurrentChat() != null && user.getCurrentChat().getId().equals(chat.getId()) &&
					!user.getSessionId().equals(sessionIdAuthor)) {
				usersFromChatWithoutAuthor.add(user.getSessionId());
			}
		});
		WebsocketMessage websocketMessage = WebsocketMessage.builder().messageType(MessageType.MESSAGE)
				.content(message.toMessageDTO()).build();
		userUtilCommun.sendToUsers(websocketMessage, usersFromChatWithoutAuthor);
	}
	
	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}
	
	public List<Chat> getChatThatContainsUser(User user) {
		return chatRepository.findAll()
				.stream().filter(chat -> chat.getUsers().contains(user))
				.collect(Collectors.toList());
	}
	
	public Chat getChatById(Long chatId) {
		return chatRepository.findById(chatId).orElseThrow(() -> 
			new InternalError("Chat with id: " + chatId + " doesn't exist"));
	}
	
	public void deleteChat(Chat chat) {
		chatRepository.delete(chat);
	}
}
