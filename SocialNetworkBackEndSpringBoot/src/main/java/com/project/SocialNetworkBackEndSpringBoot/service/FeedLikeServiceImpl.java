package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.*;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedLikeRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Log4j2
public class FeedLikeServiceImpl implements FeedLikeService{

    @PersistenceContext
    EntityManager em;

    private final FeedLikeRepository feedLikeRepository;

    private final NotificationService notificationService;

    private final FeedRepository feedRepository;

    @Override
    public boolean FeedLikeChk(Long feedId, String memberId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QFeedLike qFeedLike = QFeedLike.feedLike;


        boolean feedLikeChk = queryFactory.selectFrom(qFeedLike)
                .where(qFeedLike.member.memberId.eq(memberId).and(qFeedLike.feed.feedId.eq(feedId)))
                .fetch().isEmpty();


        return feedLikeChk;
    }

    @Override
    public boolean insertFeedLike(Long feedId, String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification = QNotification.notification;


        try{
            Feed feed = feedRepository.findById(feedId).orElseThrow();

            Member sendMember = Member.builder()
                    .memberId(memberId)
                    .build();

            FeedLike feedLike = FeedLike.builder()
                    .feed(feed)
                    .member(sendMember)
                    .build();

            feedLikeRepository.save(feedLike);



            boolean viewChk = jpaQueryFactory.selectFrom(qNotification)
                    .where(qNotification.feedLike.feedId.eq(feed.getFeedId())
                            .and(qNotification.sendMember.memberId.eq(sendMember.getMemberId()))
                            .and(qNotification.viewChk.isFalse()))
                    .fetch().stream().count() == 0;


            if(viewChk) {

                NotificationDTO notificationDTO = NotificationDTO.builder()
                        .sendMemberId(sendMember.getMemberId())
                        .readMemberId(feed.getMember().getMemberId())
                        .notificationMessageType(1)
                        .likeFeedId(feed.getFeedId())
                        .build();

                notificationService.createNotification(notificationDTO);
            }
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteFeedLike(Long feedId, String memberId) {


        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFeedLike qFeedLike = QFeedLike.feedLike;

        try{
            jpaQueryFactory.delete(qFeedLike)
                    .where(qFeedLike.member.memberId.eq(memberId)
                            .and(qFeedLike.feed.feedId.eq(feedId)))
                    .execute();

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
