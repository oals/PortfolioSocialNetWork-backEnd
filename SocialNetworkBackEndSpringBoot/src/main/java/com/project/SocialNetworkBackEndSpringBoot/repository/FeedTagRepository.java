package com.project.SocialNetworkBackEndSpringBoot.repository;

import com.project.SocialNetworkBackEndSpringBoot.entity.FeedTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FeedTagRepository extends JpaRepository<FeedTag,Long>, QuerydslPredicateExecutor<FeedTag> {
}
