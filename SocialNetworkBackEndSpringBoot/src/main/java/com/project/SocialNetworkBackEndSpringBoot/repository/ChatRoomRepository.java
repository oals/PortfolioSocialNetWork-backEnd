package com.project.SocialNetworkBackEndSpringBoot.repository;

import com.project.SocialNetworkBackEndSpringBoot.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long>, QuerydslPredicateExecutor<ChatRoom> {
}
