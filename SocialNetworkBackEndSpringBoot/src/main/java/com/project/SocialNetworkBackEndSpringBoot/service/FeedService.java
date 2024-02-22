package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.*;

import java.util.List;

public interface FeedService {


    FeedInfoDTO selectFeedInfo(String memberId);

    FeedItemInfoDTO selectFeedItemInfo(Long feedId);

    List<FeedListDTO> selectTagFeed(String TagName);


    List<Long> selectRandomFeedItem(Long mainFeedLimit,String memberId);

    List<FollowDTO> selectRandomFollow(String memberId);

    FeedListDTO insertFeed(FeedFormDTO feedFormDTO);
}
