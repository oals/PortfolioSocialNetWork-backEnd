package com.project.SocialNetworkBackEndSpringBoot.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedCommentDTO {

    private Long feedCommentId;

    private String feedCommentContent;

    private String feedCommentDate;

    private Long feedCommentParent;

    private String memberId;

    private String memberProfileImage;

    private Long feedId;


}
