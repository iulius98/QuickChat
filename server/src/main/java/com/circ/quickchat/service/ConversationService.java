package com.circ.quickchat.service;

import java.util.List;
import java.util.stream.Collectors;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Group;
import com.circ.quickchat.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Conversation;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.ConversationRepository;

@Service
public class ConversationService {
	
	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private UserService userService;

	public Conversation save(Conversation conversation) {
		Chat chatDb = chatRepository.save(conversation.getChat());
		conversation.setId(chatDb.getId());
		conversation.setChat(chatDb);
		return conversationRepository.save(conversation);
	}

	public void delete(Conversation conversation) {
		userService.saveAll(
				conversation.getChat().getUsers().stream()
				.filter(usr -> usr.getCurrentChat() != null && usr.getCurrentChat().equals(conversation.getChat())).map(usr -> {
					usr.setCurrentChat(null);
					return usr;
				}).collect(Collectors.toList()));
		conversationRepository.delete(conversation);
	}
	
	public Conversation findById(Long id) {
		return conversationRepository.findById(id).orElseThrow(() -> new InternalError("it doesn't exist a conversation with this id!"));
	}

	public List<Conversation> getChatThatContainsUser(User user) {
		return conversationRepository.findAll()
				.stream().filter(conv -> conv.getChat().getUsers().stream()
						.anyMatch(usr -> usr.getId().equals(user.getId())))
				.collect(Collectors.toList());
	}
}
