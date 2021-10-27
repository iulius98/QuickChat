package com.circ.quickchat.repositories;

import com.circ.quickchat.entity.ConversationInfo;
import com.circ.quickchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationInfoRepository extends JpaRepository<ConversationInfo, Long> {
    public List<ConversationInfo> findAllByUserId(Long userId);
}
