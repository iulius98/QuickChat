package com.circ.quickchat.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.circ.quickchat.entity.Group;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.repositories.UserRepository;
import com.circ.quickchat.utils.Alerts.ChatAllert;

import DTO.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupService groupService;

	@Autowired
	private ChatAllert chatAllert;

	public void addUserInChat(Group chat, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new InternalError("It doesn't exist an user with" + " the id: " + userId));
		chatAllert.addUserInChat(chat, user);
		groupService.save(chat);
	}
	
	public Group addUsersInChat(Group group, List<Long> usersId) {
		group.getChat().setUsers(new HashSet<User>());
		Group temporaryGroupDb = groupService.save(group);
		Set<User> users = getAllForIds(usersId);
		temporaryGroupDb.getChat().setUsers(users);
		temporaryGroupDb = groupService.save(temporaryGroupDb);
		users.forEach(usr -> {
			chatAllert.addUserInChatV2(group, usr);
		});
		return groupService.save(group);
	}

	public User getUserBySessionId(String sessionId) {
		return userRepository.findOneBySessionId(sessionId)
				.orElseThrow(() -> new InternalError("User that create the chat isn't " + "into database"));
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(User user) {
		userRepository.deleteById(user.getId());
	}

	public List<UserDTO> getUsers(String sessionId) {
		return userRepository.findAll().stream().filter(user -> !user.getSessionId().equals(sessionId))
				.map(user -> user.toUserDTO()).collect(Collectors.toList());
	}
	
	public void saveAll(Collection<User> users) {
		userRepository.saveAll(users);
	}
	
	public Set<User> getAllForIds(List<Long> ids) {
		return userRepository.findAllById(ids).stream()
				.collect(Collectors.toSet());
	}
	
	public User getUserForId(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new InternalError("Doesn exist an user with thid id!"));
	}

}
