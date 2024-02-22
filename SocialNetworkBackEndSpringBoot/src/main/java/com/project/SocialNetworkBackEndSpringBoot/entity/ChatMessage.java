package com.project.SocialNetworkBackEndSpringBoot.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageNo;

    private String chatMessage;

    private String chatMessageDate;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;






}
