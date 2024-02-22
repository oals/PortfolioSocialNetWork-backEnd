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
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_member_id", referencedColumnName = "memberId")
    private Member followMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_member_id", referencedColumnName = "memberId")
    private Member followerMember;

}
