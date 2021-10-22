package com.circ.quickchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "session_id")
	private String sessionId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "created_at")
	private long createdAt;
	
	@OneToOne
	@JoinColumn(name = "photo_profile_id")
	private Photo photo;
	
	@OneToOne
	@JoinColumn(name = "current_chat_id")
	private Chat currentChat;
	
	public User() {
	}
	
	public UserDTO toUserDTO() {
		return UserDTO.builder().id(this.id)
				.name(this.name).build();
	}
	
	
	
}
