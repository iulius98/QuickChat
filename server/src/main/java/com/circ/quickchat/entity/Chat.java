package com.circ.quickchat.entity;

import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Chat {
	private String id;
	private String name;
	private Set<User> users;
	private List<Message> messages;
}
