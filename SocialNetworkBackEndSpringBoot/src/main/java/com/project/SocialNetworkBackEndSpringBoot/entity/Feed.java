package com.project.SocialNetworkBackEndSpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feed {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    private String feedContent;

    private String feedDate;

    private String feedThumnailImage;

    @OneToMany(mappedBy = "feed")
    private List<FeedComment> feedCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<FeedImage> feedImageList = new ArrayList<>();


    @OneToMany(mappedBy = "feed")
    private List<FeedTag> feedTagList = new ArrayList<>();



    @OneToMany(mappedBy = "feed")
    private List<FeedLike> feedLikeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;









}
