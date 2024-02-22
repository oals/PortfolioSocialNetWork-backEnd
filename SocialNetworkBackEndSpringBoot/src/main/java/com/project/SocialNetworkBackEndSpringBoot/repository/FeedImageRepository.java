package com.project.SocialNetworkBackEndSpringBoot.repository;

import com.project.SocialNetworkBackEndSpringBoot.entity.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FeedImageRepository extends JpaRepository<FeedImage,Long>, QuerydslPredicateExecutor<FeedImage> {
}
