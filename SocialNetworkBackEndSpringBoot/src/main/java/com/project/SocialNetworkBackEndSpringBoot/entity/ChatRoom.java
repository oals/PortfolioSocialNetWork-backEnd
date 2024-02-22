package com.project.SocialNetworkBackEndSpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String LastUseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_member_id", referencedColumnName = "memberId")
    private Member chatMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_member_id2", referencedColumnName = "memberId")
    private Member chatMemberId2;


    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessageList = new ArrayList<>();


    public void updateLastUseDate(String useDate){
        this.LastUseDate = useDate;
    }

}
