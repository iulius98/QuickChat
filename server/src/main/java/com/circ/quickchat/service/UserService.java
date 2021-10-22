package com.circ.quickchat.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Chat;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.UserRepository;
import com.circ.quickchat.utils.Alerts.ChatAllert;

import DTO.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatAllert chatAllert;

	public void addUserInChat(Chat chat, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new InternalError("It doesn't exist an user with" + " the id: " + userId));
		chatAllert.addUserInChat(chat, user);
		chatService.save(chat);
	}

	public User getUserBySessionId(String sessionId) {
		return userRepository.findBySessionId(sessionId)
				.orElseThrow(() -> new InternalError("User that create the chat isn't " + "into database"));
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public List<UserDTO> getUsers() {
		return userRepository.findAll().stream().map(user -> user.toUserDTO()).collect(Collectors.toList());
	}

}
