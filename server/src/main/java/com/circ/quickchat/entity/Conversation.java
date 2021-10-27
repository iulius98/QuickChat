package com.circ.quickchat.entity;

import java.util.List;

import javax.persistence.*;

import DTO.ConversationDTO;
import DTO.SimpleConversationDTO;
import DTO.SimpleGroupDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "conversations")
public class Conversation {
	
	@Id
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
	@JoinColumn(name = "id")
	private Chat chat;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "conversation_id")
	private List<ConversationInfo> conversationsInfo;
	
	public Conversation() {
		
	}
	
	public ConversationDTO toConversationDTO(Long userConvId) {
		ConversationInfo conversationInfoForCurrentUser = conversationsInfo.stream()
				.filter(convInfo -> convInfo.getUserId().equals(userConvId)).findAny()
				.orElseThrow(() -> new InternalError("This user isn't into covnersation"));
		return ConversationDTO.builder().id(id).users(chat.getUsers())
				.name(conversationInfoForCurrentUser.getName())
				.messages(chat.getMessages()).build();
	}
	
	public SimpleConversationDTO toSimpleConversationDTO(Long userConvId) {
		ConversationInfo conversationInfoForCurrentUser = conversationsInfo.stream()
				.filter(convInfo -> convInfo.getUserId().equals(userConvId)).findAny()
				.orElseThrow(() -> new InternalError("This user isn't into covnersation"));
		return SimpleConversationDTO.builder().id(id).name(conversationInfoForCurrentUser.getName()).build();
	}
}
