package com.circ.quickchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.circ.quickchat.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
