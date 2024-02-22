package com.project.SocialNetworkBackEndSpringBoot.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedFormDTO {

    private String memberId;
    private String feedContent;
    private List<String> feedContentTagList;
    private List<MultipartFile> feedImageList;


}
