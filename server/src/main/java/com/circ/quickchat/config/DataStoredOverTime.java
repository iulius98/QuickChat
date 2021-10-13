package com.circ.quickchat.config;

import java.util.HashMap;
import java.util.Map;

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
		return new HashMap<String, Chat>();
	}

}
