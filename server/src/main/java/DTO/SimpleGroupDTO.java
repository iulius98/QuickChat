package DTO;

import constant.ChatTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimpleGroupDTO {
	private Long id;

	@Builder.Default
	private ChatTypes type = ChatTypes.GROUP;

	private String name;
}
