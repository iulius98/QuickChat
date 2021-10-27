package com.circ.quickchat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Group;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.ChatRepository;
import com.circ.quickchat.repositories.GroupRepository;
import com.circ.quickchat.utils.communcation.UserUtilCommun;
import com.circ.quickchat.websocket.WebsocketMessage;

import constant.MessageType;

@Service
public class GroupService {
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional
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
	
	public Group save(Group group) {
		Chat chat  = group.getChat();
		Chat chatDB = chatRepository.save(chat);
		group.setChat(chatDB);
		group.setId(chatDB.getId());
		return groupRepository.save(group);
	}
	
	public List<Group> getChatThatContainsUser(User user) {
		return groupRepository.findAll()
				.stream().filter(group -> group.getChat().getUsers().stream()
						.anyMatch(usr -> usr.getId().equals(user.getId())))
				.collect(Collectors.toList());
	}
	
	public Group getGroupById(Long groupId) {
		Group group = groupRepository.findById(groupId).orElseThrow(() -> 
			new InternalError("Group with id: " + groupId + " doesn't exist"));
		group.getChat().getMessages();
		return group;
	}
	
	public void deleteGroup(Group group) {
		userService.saveAll(
		group.getChat().getUsers().stream()
		.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(group.getChat())).map(usr -> {
			usr.setCurrentChat(null);
			return usr;
		}).collect(Collectors.toList()));
		groupRepository.delete(group);
		//chatRepository.delete(group.getChat());
	}
}
