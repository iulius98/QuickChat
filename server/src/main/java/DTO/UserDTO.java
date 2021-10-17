package DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class UserDTO {
	private String id;
	private String sessionid;
	private String name;
	private long timestamp;
}
