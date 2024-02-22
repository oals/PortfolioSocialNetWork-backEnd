package com.project.SocialNetworkBackEndSpringBoot.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FollowDTO {


    private String memberId;
    private String memberProfileImage;

    private boolean followingBackChk; //맞팔 여부




}
