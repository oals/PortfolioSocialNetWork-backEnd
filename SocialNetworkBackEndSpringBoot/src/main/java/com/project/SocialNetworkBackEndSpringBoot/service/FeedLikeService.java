package com.project.SocialNetworkBackEndSpringBoot.service;

public interface FeedLikeService {


    boolean FeedLikeChk(Long feedId,String memberId);

    boolean insertFeedLike(Long feedId,String memberId);

    boolean deleteFeedLike(Long feedId,String memberId);

}
