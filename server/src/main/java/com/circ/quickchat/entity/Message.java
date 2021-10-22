package com.circ.quickchat.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import DTO.MessageDTO;
import DTO.UserDTO;
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
	@JsonIgnoreProperties("messages")	
	private Chat chat;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	
	@Column(name = "created_at")
	private Long createdAt;
	
	@Column(name = "content")
	private String content;
	
	public Message() {
		
	}
	
	public MessageDTO toMessageDTO() {
		return MessageDTO.builder().id(id).author(UserDTO.builder().id(author.getId())
				.name(author.getName()).build()).content(content).createdAt(createdAt).build();
	}
	
	/*
	 * public Message() { super.messageType = MessageType.MESSAGE; }
	 */
}
