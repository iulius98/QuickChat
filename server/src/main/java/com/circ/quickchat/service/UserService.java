package com.circ.quickchat.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.utils.Alerts.ChatAllert;
import com.circ.quickchat.utils.communcation.UserUtilCommun;

@Component
public class UserService {
	
	@Autowired
	private Map<String, User> sessionKeyToUser;
	
	@Autowired
	private UserUtilCommun userUtilCommun;
	
	@Autowired
	private ChatAllert chatAllert;
	
	public void addUserInChat(Chat chat, String userId) {
		sessionKeyToUser.entrySet().stream().forEach(entrySet -> {
			User user = entrySet.getValue();
			//String sessionIdForUser = entrySet.getKey();
			if (user.getId().equals(userId)) {
				chatAllert.addUserInChat(chat, user);
			}
		});
	
	}
	
}
