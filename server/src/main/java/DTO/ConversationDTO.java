package DTO;

import java.util.List;

import com.circ.quickchat.entity.Message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ConversationDTO {
	private Long id;
	private String name;
	private List<Message> messages;
}
