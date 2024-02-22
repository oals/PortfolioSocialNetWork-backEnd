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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationNo;

    private int notificationMessageType;

    private boolean viewChk;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "comment_feed_id", referencedColumnName = "feedId")
    private Feed feedComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_feed_id", referencedColumnName = "feedId")
    private Feed feedLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_member_id", referencedColumnName = "memberId")
    private Member follow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "read_member_id", referencedColumnName = "memberId")
    private Member readMember;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_member_id", referencedColumnName = "memberId")
    private Member sendMember;



    public void viewChkFunction(){
      this.viewChk = true;
    }




}
