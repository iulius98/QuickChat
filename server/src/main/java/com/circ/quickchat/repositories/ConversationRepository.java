package com.circ.quickchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.circ.quickchat.entity.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{

}
