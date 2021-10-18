package com.circ.quickchat.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;

import constant.ChatConstants;

@Configuration
public class DataStoredOverTime {
	
	@Bean
	public Map<String, User> sessionKeyToUser() {
		return new HashMap<>();
	}
	
	@Bean
	public Map<String, Chat> chats() {
		HashMap<String, Chat> chats = new HashMap<String, Chat>();
		chats.put(ChatConstants.principalChatId, Chat.builder().id(ChatConstants.principalChatId)
				.users(new HashSet<User>()).messages(new ArrayList<Message>()).build());
		return chats;
	}

}
