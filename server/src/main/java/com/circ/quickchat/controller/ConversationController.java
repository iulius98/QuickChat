package com.circ.quickchat.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Conversation;
import com.circ.quickchat.entity.ConversationInfo;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.ConversationService;
import com.circ.quickchat.service.UserService;

import DTO.ConversationDTO;
import DTO.SimpleConversationDTO;

@RestController(value = "/group")
public class ConversationController {
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired 
	private UserService userService;
	
	@PostMapping("/create/{sessionId}/{anotherUserId}")
	public SimpleConversationDTO createConversation(@RequestBody ConversationInfo conversationInfo, 
			@PathVariable String sessiondId, @PathVariable Long anotherUserId) {
		Set<User> userSet = new HashSet<User>();
		List<ConversationInfo> info = new ArrayList<ConversationInfo>();
		User userThatCreatedConv = userService.getUserBySessionId(sessiondId);
		conversationInfo.setUserId(userThatCreatedConv.getId());
		info.add(conversationInfo);
		info.add(ConversationInfo.builder().name(userThatCreatedConv.getName()).userId(anotherUserId).build());
		userSet.add(userThatCreatedConv);
		User anotherUser = userService.getUserForId(anotherUserId);
		userSet.add(anotherUser);
		Chat newChat = Chat.builder().users(userSet).messages(new ArrayList<Message>()).build();
		Conversation conversation = Conversation.builder().chat(newChat).conversationsInfo(info).build();
		return conversationService.save(conversation).toSimpleConversationDTO(userThatCreatedConv.getId());
	}
	
	@GetMapping("/get/{convId}/{sessionId}")
	public ConversationDTO getConversation(@PathVariable Long convId, @PathVariable String sessionId) {
		return conversationService.findById(convId)
				.toConversationDTO(userService.getUserBySessionId(sessionId).getId());
	}
	
}
