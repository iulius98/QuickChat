package DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageDTO {
	private Long id;
	private UserDTO author;
	private String content;
	private Long createdAt;
}
