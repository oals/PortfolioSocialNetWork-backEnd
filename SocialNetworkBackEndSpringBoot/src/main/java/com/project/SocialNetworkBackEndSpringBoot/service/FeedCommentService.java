package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.FeedCommentDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.FeedComment;

public interface FeedCommentService {



    FeedCommentDTO insertFeedComment(FeedCommentDTO feedCommentDTO);
}
