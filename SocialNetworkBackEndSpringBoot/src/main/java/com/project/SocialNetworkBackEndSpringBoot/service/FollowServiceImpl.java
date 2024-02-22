package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.FollowDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.Follow;
import com.project.SocialNetworkBackEndSpringBoot.entity.Member;
import com.project.SocialNetworkBackEndSpringBoot.entity.QFollow;
import com.project.SocialNetworkBackEndSpringBoot.entity.QNotification;
import com.project.SocialNetworkBackEndSpringBoot.repository.FollowRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FollowServiceImpl implements FollowService {


    @PersistenceContext
    EntityManager em;


    private final FollowRepository followRepository;

    private final NotificationService notificationService;

    @Override
    public boolean followChk(String followMember, String followerMember) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFollow qFollow = QFollow.follow;

        return jpaQueryFactory.selectFrom(qFollow)
                .where(qFollow.followMember.memberId.eq(followMember)
                        .and(qFollow.followerMember.memberId.eq(followerMember)))
                .fetchOne() != null;

    }

    @Override
    public boolean insertFollowMember(String followMember, String followerMember) {


        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification = QNotification.notification;

        try{
            Member memberFollow = Member.builder()
                    .memberId(followMember)
                    .build();

            Member memberFollower = Member.builder()
                    .memberId(followerMember)
                    .build();


            Follow follow = Follow.builder()
                    .followMember(memberFollow)
                    .followerMember(memberFollower)
                    .build();

            followRepository.save(follow);



            boolean viewChk = jpaQueryFactory.selectFrom(qNotification)
                    .where(qNotification.follow.memberId.eq(follow.getFollowerMember().getMemberId())
                            .and(qNotification.readMember.memberId.eq(follow.getFollowMember().getMemberId()))
                            .and(qNotification.viewChk.isFalse()))
                    .fetch().stream().count() == 0;


            if(viewChk) {

                NotificationDTO notificationDTO = NotificationDTO.builder()
                        .sendMemberId(memberFollower.getMemberId())
                        .readMemberId(memberFollow.getMemberId())
                        .notificationMessageType(3)
                        .followMemberId(memberFollower.getMemberId())
                        .build();

                notificationService.createNotification(notificationDTO);
            }

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Transactional
    @Override
    public boolean deleteUnFollowMember(String followMember, String followerMember) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFollow qFollow = QFollow.follow;

        try{
            jpaQueryFactory.delete(qFollow)
                    .where(qFollow.followMember.memberId.eq(followMember)
                            .and(qFollow.followerMember.memberId.eq(followerMember)))
                    .execute();

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FollowDTO> selectFollowList(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFollow qFollow = QFollow.follow;

        List<Follow> followList = jpaQueryFactory.selectFrom(qFollow)
                .where(qFollow.followerMember.memberId.eq(memberId))
                .fetch();

        List<FollowDTO> followDTOList = new ArrayList<>();

        for(int i=0; i< followList.size(); i++){

            FollowDTO followDTO = FollowDTO.builder()
                    .memberId(followList.get(i).getFollowMember().getMemberId())
                    .memberProfileImage(followList.get(i).getFollowMember().getMemberProfileImage())
                    .build();

            followDTOList.add(followDTO);
        }



        return followDTOList;
    }

    @Override
    public List<FollowDTO> selectFollowerList(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFollow qFollow = QFollow.follow;


        List<Follow> followerList = jpaQueryFactory.selectFrom(qFollow)
                .where(qFollow.followMember.memberId.eq(memberId))
                .fetch();

        //나를 팔로우 하고 있는 유저 목록

        List<FollowDTO> followerDTOList = new ArrayList<>();

        for(int i = 0; i< followerList.size(); i++){

            // 맞팔 여부 확인
            Boolean followingBack = jpaQueryFactory.selectFrom(qFollow)
                    .where(qFollow.followMember.memberId.eq(followerList.get(i).getFollowerMember().getMemberId())
                            .and(qFollow.followerMember.memberId.eq(memberId)))
                    .fetchCount() > 0;

            FollowDTO followDTO = FollowDTO.builder()
                    .memberId(followerList.get(i).getFollowerMember().getMemberId())
                    .memberProfileImage(followerList.get(i).getFollowerMember().getMemberProfileImage())
                    .followingBackChk(followingBack)
                    .build();

            followerDTOList.add(followDTO);
        }


        return followerDTOList;
    }
}
