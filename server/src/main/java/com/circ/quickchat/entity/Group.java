package com.circ.quickchat.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import DTO.GroupDTO;
import DTO.SimpleGroupDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "groups")
public class Group {
	
	@Id
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
	@JoinColumn(name = "id")
	private Chat chat;
	
	@Column(name = "group_name")
	private String name;
	
	
	public Group() {
		
	}
	
	public SimpleGroupDTO toSimpleGroupDTO() {
		return SimpleGroupDTO.builder().id(id).name(name).build();
	}
	
	public GroupDTO toGroupDTO() {
		return GroupDTO.builder()
				.id(id).name(name).users(chat.getUsers())
				.messages(chat.getMessages()).build();
	}
	
	
}
