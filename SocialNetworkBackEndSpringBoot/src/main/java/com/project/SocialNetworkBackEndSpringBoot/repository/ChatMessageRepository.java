package com.project.SocialNetworkBackEndSpringBoot.repository;

import com.project.SocialNetworkBackEndSpringBoot.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long>, QuerydslPredicateExecutor<ChatMessage> {
}
