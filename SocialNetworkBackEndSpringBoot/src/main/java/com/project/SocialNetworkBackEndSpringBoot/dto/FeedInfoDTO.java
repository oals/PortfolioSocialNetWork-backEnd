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
public class FeedInfoDTO {

    private String memberId;
    private String memberName;
    private String memberEmail;

    private String memberJobTitle;
    private String memberSchoolName;
    private String memberProfileImage;

    private Long feedCount;
    private Long followCount;
    private Long followerCount;

    private boolean followChk;

    private List<FeedListDTO> feedListDTOS;


}
