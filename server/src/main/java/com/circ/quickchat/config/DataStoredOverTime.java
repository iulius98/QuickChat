package com.circ.quickchat.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import constant.ChatConstants;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.User;

@Configuration
public class DataStoredOverTime {
	
	@Bean
	public Map<String, User> sessionKeyToUser() {
		return new HashMap<>();
	}
	
	@Bean
	public Map<String, Chat> chats() {
		HashMap<String, Chat> chats = new HashMap<String, Chat>();
		chats.put(ChatConstants.principalChatId, Chat.builder().id(UUID.randomUUID().toString()).build());
		return chats;
	}

}
