package DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SimpleConversationDTO {
	private Long id;
	private String name;
}
