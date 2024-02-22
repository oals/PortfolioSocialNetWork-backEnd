package com.project.SocialNetworkBackEndSpringBoot.service;


import com.project.SocialNetworkBackEndSpringBoot.config.SimpleTextHandler;
import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.*;
import com.project.SocialNetworkBackEndSpringBoot.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService{

    @PersistenceContext
    EntityManager em;



    private final NotificationRepository notificationRepository;
    private final SimpleTextHandler simpleTextHandler;


    private final MemberRepository memberRepository;
    
    private final FeedRepository feedRepository;

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public boolean createNotification(NotificationDTO notificationDTO) {

        if(notificationDTO.getSendMemberId().equals(notificationDTO.getReadMemberId())){
            return true;
        }

        try{
            Member sendMember = Member.builder()
                    .memberId(notificationDTO.getSendMemberId())
                    .build();

            Member readMember = Member.builder()
                    .memberId(notificationDTO.getReadMemberId())
                    .build();



            Feed feedLikeData = null;
            Feed commentData = null;
            Member followData = null;
            ChatRoom chatRoomData = null;

            if(notificationDTO.getNotificationMessageType() == 1){
                feedLikeData = feedRepository.findById(notificationDTO.getLikeFeedId()).orElseThrow();
            }else if(notificationDTO.getNotificationMessageType() == 2){
                commentData = feedRepository.findById(notificationDTO.getCommentFeedId()).orElseThrow();
            }else if(notificationDTO.getNotificationMessageType() == 3){
                followData = memberRepository.findById(notificationDTO.getFollowMemberId()).orElseThrow();
            }else if(notificationDTO.getNotificationMessageType() == 4) {
                chatRoomData = chatRoomRepository.findById(notificationDTO.getChatRoomId()).orElseThrow();
            }

            Notification notification = Notification.builder()
                    .notificationMessageType(notificationDTO.getNotificationMessageType())
                    .sendMember(sendMember)
                    .readMember(readMember)
                    .feedLike(feedLikeData) // 해당 피드
                    .feedComment(commentData) //해당 피드
                    .follow(followData)  //해당 멤버 정보
                    .chatRoom(chatRoomData)
                    .build();

            notificationRepository.save(notification);

            simpleTextHandler.sendMessageToAll(notificationDTO.getSendMemberId(),notificationDTO.getReadMemberId());

            // 알림 생성 후 실시간으로 전송
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public List<NotificationDTO> selectMyNotification(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification = QNotification.notification;


        List<Notification> query = jpaQueryFactory.selectFrom(qNotification)
                .where(qNotification.readMember.memberId.eq(memberId))
                .orderBy(qNotification.notificationNo.desc())
                .fetch();


        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(int i = 0; i < query.size(); i++){

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setNotificationNo(query.get(i).getNotificationNo());
            notificationDTO.setNotificationMessageType(query.get(i).getNotificationMessageType());
            notificationDTO.setSendMemberId(query.get(i).getSendMember().getMemberId());
            notificationDTO.setSendMemberProfileImage(query.get(i).getSendMember().getMemberProfileImage());
            notificationDTO.setReadMemberId(query.get(i).getReadMember().getMemberId());
            notificationDTO.setViewChk(query.get(i).isViewChk());

            if(query.get(i).getNotificationMessageType() == 1){
                notificationDTO.setLikeFeedId(query.get(i).getFeedLike().getFeedId());
            }else if(query.get(i).getNotificationMessageType() == 2){
                notificationDTO.setCommentFeedId(query.get(i).getFeedComment().getFeedId());
            }else if(query.get(i).getNotificationMessageType() == 3){
                notificationDTO.setFollowMemberId(query.get(i).getFollow().getMemberId());
            }else if(query.get(i).getNotificationMessageType() == 4){
                notificationDTO.setChatRoomId(query.get(i).getChatRoom().getChatRoomId());
            }


            notificationDTOS.add(notificationDTO);


        }

        return notificationDTOS;
    }

    @Override
    public boolean notificationViewChk(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification =QNotification.notification;


        return jpaQueryFactory.selectFrom(qNotification)
                .where(qNotification.readMember.memberId.eq(memberId)
                        .and(qNotification.viewChk.isFalse()))
                .fetch().stream().count() > 0;



    }

    @Override
    public void notificationViewUpdate(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification = QNotification.notification;


        List<Notification> query = jpaQueryFactory.selectFrom(qNotification)
                .where(qNotification.readMember.memberId.eq(memberId)
                        .and(qNotification.viewChk.isFalse()))
                .fetch();

        for(int i = 0; i < query.size(); i++) {
            query.get(i).viewChkFunction();
        }

        notificationRepository.saveAll(query);
    }
}
