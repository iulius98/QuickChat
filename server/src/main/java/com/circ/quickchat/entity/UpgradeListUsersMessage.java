package com.circ.quickchat.entity;

import java.util.List;

import javax.annotation.PostConstruct;

import constant.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UpgradeListUsersMessage extends GeneralMessage<List<User>> {
	
	private boolean isCome;
	private String changedUser;
	
}
