package com.circ.quickchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	
	@Column(name = "created_at")
	private Long createdAt;
	
	@Column(name = "content")
	private String content;
	
	/*
	 * public Message() { super.messageType = MessageType.MESSAGE; }
	 */
}
