package com.circ.quickchat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Conversation;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.ConversationRepository;

@Service
public class ConversationService {
	
	@Autowired
	ConversationRepository conversationRepository;
	
	public Conversation save(Conversation conversation) {
		return conversationRepository.save(conversation);
	}
	
	public Conversation findById(Long id) {
		return conversationRepository.findById(id).orElseThrow(() -> new InternalError("it doesn't exist a conversation with this id!"));
	}
}
