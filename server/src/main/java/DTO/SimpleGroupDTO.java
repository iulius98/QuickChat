package DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimpleGroupDTO {
	private Long id;
	private String name;
}
