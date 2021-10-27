package DTO;

import constant.ChatTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Builder
public class SimpleConversationDTO {
	private Long id;

	@Builder.Default
	private ChatTypes type = ChatTypes.CONVERSATION;

	private String name;
}
