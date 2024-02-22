package com.project.SocialNetworkBackEndSpringBoot.dto;


import com.project.SocialNetworkBackEndSpringBoot.entity.ChatRoom;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessageDTO {

    private Long chatMessageNo;

    private String chatMessage;

    private String chatMessageDate;
    
    private String memberId;//작성자


    private Long chatRoomId;


}
