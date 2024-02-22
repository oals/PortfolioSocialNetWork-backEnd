package com.project.SocialNetworkBackEndSpringBoot.repository;

import com.project.SocialNetworkBackEndSpringBoot.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FeedLikeRepository extends JpaRepository<FeedLike,Long>, QuerydslPredicateExecutor<FeedLike> {
}
