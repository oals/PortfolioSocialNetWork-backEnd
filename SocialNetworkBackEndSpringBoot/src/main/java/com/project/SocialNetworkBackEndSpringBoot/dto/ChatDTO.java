package com.project.SocialNetworkBackEndSpringBoot.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDTO {

    private String memberId; //대화 상대

    private String memberProfileImage;

    private Long chatRoomId;

    private List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();
}
