package com.project.SocialNetworkBackEndSpringBoot.dto;


import com.project.SocialNetworkBackEndSpringBoot.entity.ChatMessage;
import com.project.SocialNetworkBackEndSpringBoot.entity.Follow;
import com.project.SocialNetworkBackEndSpringBoot.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRoomDTO {


    private Long chatRoomId;

    private String LastUseDate;

    private String memberId; //상대방 아이디

    private String memberProfileImage;

    private List<ChatMessageDTO> chatMessageList = new ArrayList<>();


}
