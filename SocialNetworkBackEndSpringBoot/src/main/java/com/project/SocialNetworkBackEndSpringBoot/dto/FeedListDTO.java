package com.project.SocialNetworkBackEndSpringBoot.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedListDTO {

    private Long feedId;

    private String feedThumnailImage;

    private Long feedLikeCount;

    private Long feedCommentCount;


}
