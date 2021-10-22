package com.circ.quickchat.websocket;


import DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class UserAndChat {
	protected UserDTO user;
	protected Long chatId;
}
