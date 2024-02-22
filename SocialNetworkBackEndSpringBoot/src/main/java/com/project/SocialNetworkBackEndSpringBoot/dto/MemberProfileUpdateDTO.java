package com.project.SocialNetworkBackEndSpringBoot.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberProfileUpdateDTO {

    private String memberId;
    private String memberName;
    private String memberJobTitle;
    private String memberSchoolName;
    private MultipartFile memberProfileImage;
}
