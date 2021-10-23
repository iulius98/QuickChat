package com.circ.quickchat.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
@Table(name = "messages")
@SuperBuilder(toBuilder = true)
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "chat_id")
	@JsonIgnoreProperties("messages")	
	private Chat chat;
	
	@Column(name = "author_id")
	private Long authorId;
	
	@Column(name = "author_name")
	private String authorName;
	
	@Column(name = "created_at")
	private Long createdAt;
	
	@Column(name = "content")
	private String content;
	
	public Message() {
		
	}
	
	public MessageDTO toMessageDTO() {
		return MessageDTO.builder().id(id).authorId(authorId)
				.authorName(authorName).content(content).createdAt(createdAt).build();
	}
	
	/*
	 * public Message() { super.messageType = MessageType.MESSAGE; }
	 */
}
