package com.circ.quickchat.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "groups")
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
		return ConversationDTO.builder().id(id).name(conversationInfoForCurrentUser.getName())
				.messages(chat.getMessages()).build();
	}
	
	public SimpleConversationDTO toSimpleConversationDTO(Long userConvId) {
		ConversationInfo conversationInfoForCurrentUser = conversationsInfo.stream()
				.filter(convInfo -> convInfo.getUserId().equals(userConvId)).findAny()
				.orElseThrow(() -> new InternalError("This user isn't into covnersation"));
		return SimpleConversationDTO.builder().id(id).name(conversationInfoForCurrentUser.getName()).build();
	}
}
