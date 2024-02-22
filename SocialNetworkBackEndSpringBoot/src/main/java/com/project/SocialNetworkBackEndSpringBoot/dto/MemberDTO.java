package com.project.SocialNetworkBackEndSpringBoot.dto;

import com.project.SocialNetworkBackEndSpringBoot.config.Role;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDTO {

    private String memberId;

    private String memberPswd;
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberAddress;
    private String memberDate;
    private String memberJobTitle;
    private String memberSchoolName;

    private String memberProfileImage;
    private String createDate;

}
