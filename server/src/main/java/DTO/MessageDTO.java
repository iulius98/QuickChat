package DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageDTO {
	private Long id;
	private Long authorId;
	private String authorName;
	private String content;
	private Long createdAt;
}
