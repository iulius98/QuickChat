package com.circ.quickchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "conversation_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "chat_name")
	private String name;

	public ConversationInfo() {

	}
}
