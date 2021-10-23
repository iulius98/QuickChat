package com.circ.quickchat.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	private Long createdAt;
	
	@OneToOne
	@JoinColumn(name = "photo_profile_id")
	private Photo photo;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "current_chat_id")
	@JsonIgnoreProperties({"users", "messages"})
	private Chat currentChat;
	
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
