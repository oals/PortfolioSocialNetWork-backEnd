package com.project.SocialNetworkBackEndSpringBoot.dto;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedTagDTO {

    private Long feedTagId;

    private String feedTagName;
}
