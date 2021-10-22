package com.circ.quickchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.circ.quickchat.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{

}
