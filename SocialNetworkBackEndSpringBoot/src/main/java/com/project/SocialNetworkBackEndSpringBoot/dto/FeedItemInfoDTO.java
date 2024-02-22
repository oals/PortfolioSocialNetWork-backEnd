package com.project.SocialNetworkBackEndSpringBoot.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedItemInfoDTO {

    private Long feedId;

    private String memberProfileImage;

    private String memberId; //작성자

    private String feedContent;

    private String feedDate;

    private int feedLikeCount;

    private boolean feedLikeChK;

    private List<FeedImageDTO> feedImageList;

    private List<FeedCommentDTO> feedCommentList;

    private List<FeedTagDTO> feedTagList;


}
