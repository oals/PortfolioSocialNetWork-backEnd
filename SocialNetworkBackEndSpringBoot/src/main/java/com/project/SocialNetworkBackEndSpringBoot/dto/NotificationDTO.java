package com.project.SocialNetworkBackEndSpringBoot.dto;

import com.project.SocialNetworkBackEndSpringBoot.entity.FeedComment;
import com.project.SocialNetworkBackEndSpringBoot.entity.FeedLike;
import com.project.SocialNetworkBackEndSpringBoot.entity.Follow;
import com.project.SocialNetworkBackEndSpringBoot.entity.Member;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationDTO {

    private Long notificationNo;

    private int notificationMessageType;

    private boolean viewChk;

    private Long commentFeedId;

    private Long likeFeedId;

    private String followMemberId;

    private String readMemberId;

    private Long chatRoomId;

    private String sendMemberId;

    private String sendMemberProfileImage;





}
