package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.ChatDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.ChatMessageDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.ChatRoomDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.ChatMessage;

import java.util.List;

public interface ChatRoomService {


    ChatRoomDTO createChatRoom(String memberId, String memberId2);

    List<ChatRoomDTO> chatRoomList(String memberId);

    ChatDTO selectChatMessage(Long chatRoomId, String memberId);


    ChatMessageDTO insertChatMessage(ChatMessageDTO chatMessageDTO);

}
