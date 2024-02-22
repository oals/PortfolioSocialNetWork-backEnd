package com.project.SocialNetworkBackEndSpringBoot.repository;

import com.project.SocialNetworkBackEndSpringBoot.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FeedCommentRepository extends JpaRepository<FeedComment,Long>, QuerydslPredicateExecutor<FeedComment> {
}
