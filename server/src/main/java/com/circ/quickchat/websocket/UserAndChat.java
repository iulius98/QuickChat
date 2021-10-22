package com.circ.quickchat.websocket;

import DTO.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserAndChat {
	private final UserDTO userDTO;
	private final Long chatId;
}
