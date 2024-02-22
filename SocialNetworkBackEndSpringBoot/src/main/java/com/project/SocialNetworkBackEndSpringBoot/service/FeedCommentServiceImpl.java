package com.project.SocialNetworkBackEndSpringBoot.service;


import com.project.SocialNetworkBackEndSpringBoot.dto.FeedCommentDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.Feed;
import com.project.SocialNetworkBackEndSpringBoot.entity.FeedComment;
import com.project.SocialNetworkBackEndSpringBoot.entity.Member;
import com.project.SocialNetworkBackEndSpringBoot.entity.QNotification;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedCommentRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Log4j2
public class FeedCommentServiceImpl implements FeedCommentService{


    @PersistenceContext
    EntityManager em;
    private final NotificationService notificationService;
    private final FeedCommentRepository feedCommentRepository;

    private final FeedRepository feedRepository;

    private final MemberRepository memberRepository;
    @Override
    public FeedCommentDTO insertFeedComment(FeedCommentDTO feedCommentDTO) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification = QNotification.notification;

        try{
            Member sendMember = memberRepository.findById(feedCommentDTO.getMemberId()).orElseThrow();


            Feed feed = feedRepository.findById(feedCommentDTO.getFeedId()).orElseThrow();


            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String commentDate = now.format(formatter);

            FeedComment feedComment = FeedComment.builder()
                    .feedCommentContent(feedCommentDTO.getFeedCommentContent())
                    .feedCommentDate(commentDate)
                    .feedCommentParent(feedCommentDTO.getFeedCommentParent())
                    .feed(feed)
                    .member(sendMember)
                    .build();


            feedCommentRepository.save(feedComment);

            FeedCommentDTO feedCommentDTO1 = FeedCommentDTO.builder()
                    .feedCommentId(feedComment.getFeedCommentId())
                    .feedCommentParent(feedComment.getFeedCommentParent())
                    .feedCommentContent(feedComment.getFeedCommentContent())
                    .feedCommentDate(feedComment.getFeedCommentDate())
                    .memberId(sendMember.getMemberId())
                    .memberProfileImage(sendMember.getMemberProfileImage())
                    .feedId(feed.getFeedId())
                    .build();




            boolean viewChk = jpaQueryFactory.selectFrom(qNotification)
                    .where(qNotification.feedComment.feedId.eq(feedCommentDTO.getFeedId())
                            .and(qNotification.sendMember.memberId.eq(feedCommentDTO.getMemberId()))
                            .and(qNotification.viewChk.isFalse()))
                    .fetch().stream().count() == 0;


            if(viewChk) {
                NotificationDTO notificationDTO = NotificationDTO.builder()
                        .sendMemberId(sendMember.getMemberId())
                        .readMemberId(feed.getMember().getMemberId())
                        .notificationMessageType(2)
                        .commentFeedId(feed.getFeedId())
                        .build();

                notificationService.createNotification(notificationDTO);
            }

            return feedCommentDTO1;
        }catch(Exception e){
            e.printStackTrace();

            return null;
        }




    }
}
