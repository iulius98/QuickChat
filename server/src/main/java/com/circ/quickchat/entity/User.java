package com.circ.quickchat.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@Getter
@Setter
public class User {
	
	private String id;
	
	@JsonIgnoreProperties
	private String sessionId;
	
	private String name;
	
	private long timestamp;
	
	@JsonIgnoreProperties
	private boolean isConnect = false;
	
	public User() {
	}
	
	
	
}
