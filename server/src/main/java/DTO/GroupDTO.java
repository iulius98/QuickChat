package DTO;

import java.util.List;
import java.util.Set;

import com.circ.quickchat.entity.Message;
import com.circ.quickchat.entity.User;

import constant.ChatTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupDTO {
	private Long id;
	private String name;

	@Builder.Default
	private ChatTypes type = ChatTypes.GROUP;

	private List<Message> messages;
	private Set<User> users;
}
