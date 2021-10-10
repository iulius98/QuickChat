package com.circ.quickchat.controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.circ.quickchat.entity.User;

@RestController
public class UserController {
	
	@Autowired
	Map<String, User> sessionKeyToUser;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/user/create")
	public User createTemporaryUser(@RequestBody User user) {
		user.setId(UUID.randomUUID().toString());
		sessionKeyToUser.put(user.getId(), user);
		return user;
	}
}
