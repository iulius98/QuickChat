package DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class UserDTO {
	private String id;
	private String sessionId;
	private String name;
	private long timestamp;
}
