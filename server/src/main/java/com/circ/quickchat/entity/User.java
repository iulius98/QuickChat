package com.circ.quickchat.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "session_id")
	private String sessionId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@OneToOne
	@JoinColumn(name = "photo_profile_id")
	private Photo photo;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "current_chat_id")
	@JsonIgnoreProperties({"users", "messages"})
	private Chat currentChat;

/*	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "users_to_chat",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "chat_id")
	)
	@JsonIgnoreProperties("users")
	private List<Chat> chats;*/
	
	public User() {
		
	}
	
	public UserDTO toUserDTO() {
		return UserDTO.builder().id(this.id)
				.name(this.name).build();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User objUser = (User) obj;
			return this.id.equals(objUser.getId());
		}
		return false;
	}
	
	
	
}
