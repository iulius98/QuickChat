package com.circ.quickchat.utils.communcation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.circ.quickchat.entity.GeneralMessage;
import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;

@Component
public class UserUtilCommun {

	@Autowired
	private Map<String, User> sessionKeyToUser;

	@Autowired
	public SimpMessageSendingOperations send;

	public void sendToUsersWithExcept(String sessionIdExcept, Object message) {
		sessionKeyToUser.keySet().stream().forEach(key -> {
			if (!key.equals(sessionIdExcept)) {
				send.convertAndSendToUser(key, "/usertell", message);
			}
		});
	}

	public void sendToAllUsers(Object message) {
		sessionKeyToUser.keySet().stream().forEach(key -> {
			send.convertAndSendToUser(key, "/usertell", message);
		});
	}

}
