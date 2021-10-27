package com.circ.quickchat.service;

import com.circ.quickchat.entity.ConversationInfo;
import com.circ.quickchat.repositories.ConversationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ConversationInfoService {

    @Autowired
    private ConversationInfoRepository conversationInfoRepository;

    public void deleteAll(Collection<ConversationInfo> conversationsInfo) {
        conversationInfoRepository.deleteAll(conversationsInfo);
    }

    public List<ConversationInfo> findAllByUserId(Long userId) {
        return conversationInfoRepository.findAllByUserId(userId);
    }
}
