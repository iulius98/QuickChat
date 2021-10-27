package DTO;

import java.util.List;
import java.util.Set;

import com.circ.quickchat.entity.Message;

import com.circ.quickchat.entity.User;
import constant.ChatTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.crossstore.ChangeSetBackedTransactionSynchronization;

@Getter
@Setter
@Builder
public class ConversationDTO {
	private Long id;
	private String name;

	@Builder.Default
	private ChatTypes type = ChatTypes.CONVERSATION;

	private List<Message> messages;
	private Set<User> users;
}
