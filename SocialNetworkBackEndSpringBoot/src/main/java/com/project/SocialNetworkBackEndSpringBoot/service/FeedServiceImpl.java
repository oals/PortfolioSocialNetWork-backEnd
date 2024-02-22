package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.*;
import com.project.SocialNetworkBackEndSpringBoot.entity.*;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedImageRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedLikeRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.FeedTagRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{

    @PersistenceContext
    EntityManager em;


    private final FeedLikeService feedLikeService;
    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;

    private final FeedTagRepository feedTagRepository;
    private final FileService fileService;

    private final FeedLikeRepository feedLikeRepository;

    @Override
    public FeedInfoDTO selectFeedInfo(String memberId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember qMember = QMember.member;
        QFeed qFeed = QFeed.feed;
        QFollow qFollow = QFollow.follow;

        JPQLQuery<Long> followCount = new JPAQuery<Long>()
                .select(qFollow.count())
                .from(qFollow)
                .where(qFollow.followerMember.memberId.eq(memberId));

        JPQLQuery<Long> followerCount = new JPAQuery<Long>()
                .select(qFollow.count())
                .from(qFollow)
                .where(qFollow.followMember.memberId.eq(memberId));


        Tuple query = queryFactory.select(
                        qMember.memberId,
                        qMember.memberName,
                        qMember.memberEmail,
                        qMember.memberJobTitle,
                        qMember.memberSchoolName,
                        qMember.memberProfileImage,
                        qFeed.count(),
                        followCount,
                        followerCount)
                .from(qMember)
                .where(qMember.memberId.eq(memberId))
                .leftJoin(qFeed)
                .on(qMember.memberId.eq(qFeed.member.memberId))
                .fetchOne();




        List<Tuple> queryList = queryFactory.select(
                qFeed.feedId,
                qFeed.feedThumnailImage,
                qFeed.feedLikeList.size(),
                qFeed.feedCommentList.size()
        ).from(qFeed)
                .where(qFeed.member.memberId.eq(memberId))
                .fetch();

        List<FeedListDTO> feedlist = new ArrayList<>();
        for(int i = 0; i < queryList.size(); i++){
            FeedListDTO feedListDTO = FeedListDTO.builder()
                    .feedId(queryList.get(i).get(qFeed.feedId))
                    .feedCommentCount((long) queryList.get(i).get(qFeed.feedCommentList.size()))
                    .feedLikeCount((long) queryList.get(i).get(qFeed.feedLikeList.size()))
                    .feedThumnailImage(queryList.get(i).get(qFeed.feedThumnailImage))
                    .build();
            feedlist.add(feedListDTO);
        }


        FeedInfoDTO feedInfoDTO = FeedInfoDTO.builder()
                .memberId(query.get(qMember.memberId))
                .memberName(query.get(qMember.memberName))
                .memberEmail(query.get(qMember.memberEmail))
                .memberProfileImage(query.get(qMember.memberProfileImage))
                .memberJobTitle(query.get(qMember.memberJobTitle))
                .memberSchoolName(query.get(qMember.memberSchoolName))
                .followCount(query.get(followCount))
                .followerCount(query.get(followerCount))
                .feedCount(query.get(qFeed.count()))
                .feedListDTOS(feedlist)
                .build();


        return feedInfoDTO;
    }

    @Override
    public FeedItemInfoDTO selectFeedItemInfo(Long feedId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QFeed qFeed = QFeed.feed;




        Feed feed = queryFactory.selectFrom(qFeed)
                .where(qFeed.feedId.eq(feedId))
                .fetchOne();



        List<FeedCommentDTO> FeedCommentDTOList = new ArrayList<>();
        List<FeedTagDTO> FeedTagDTOList = new ArrayList<>();
        List<FeedImageDTO> FeedImageDTOList = new ArrayList<>();


        for(int i = 0; i < feed.getFeedCommentList().size(); i++){
            FeedCommentDTO feedCommentDTO = FeedCommentDTO.builder()
                    .feedCommentId(feed.getFeedCommentList().get(i).getFeedCommentId())
                    .feedCommentContent(feed.getFeedCommentList().get(i).getFeedCommentContent())
                    .feedCommentDate(feed.getFeedCommentList().get(i).getFeedCommentDate())
                    .memberId(feed.getFeedCommentList().get(i).getMember().getMemberId())
                    .memberProfileImage(feed.getFeedCommentList().get(i).getMember().getMemberProfileImage())
                    .feedId(feed.getFeedId())
                    .build();

            FeedCommentDTOList.add(feedCommentDTO);
        }


        for(int i = 0; i < feed.getFeedTagList().size(); i++){
            FeedTagDTO feedTagDTO = FeedTagDTO.builder()
                    .feedTagId(feed.getFeedTagList().get(i).getFeedTagId())
                    .feedTagName(feed.getFeedTagList().get(i).getFeedTagName())
                    .build();

            FeedTagDTOList.add(feedTagDTO);
        }


        for(int i = 0; i < feed.getFeedImageList().size(); i++){
            FeedImageDTO feedImageDTO = FeedImageDTO.builder()
                    .feedImageNo(feed.getFeedImageList().get(i).getFeedImageNo())
                    .feedImage(feed.getFeedImageList().get(i).getFeedImage())
                    .build();

            FeedImageDTOList.add(feedImageDTO);
        }


        FeedItemInfoDTO feedItemInfoDTO = FeedItemInfoDTO.builder()
                .feedId(feed.getFeedId())
                .memberProfileImage(feed.getMember().getMemberProfileImage())
                .memberId(feed.getMember().getMemberId())
                .feedContent(feed.getFeedContent())
                .feedDate(feed.getFeedDate())
                .feedLikeCount(feed.getFeedLikeList().size())
                .feedCommentList(FeedCommentDTOList)
                .feedImageList(FeedImageDTOList)
                .feedTagList(FeedTagDTOList)
                .build();


        return feedItemInfoDTO;
    }

    @Override
    public List<FeedListDTO> selectTagFeed(String TagName) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFeedTag qFeedTag = QFeedTag.feedTag;
        QFeed qFeed = QFeed.feed;

        List<Long> feedIdlist = jpaQueryFactory.select(qFeed.feedId).from(qFeedTag)
                .where(qFeedTag.feedTagName.eq(TagName))
                .fetch();

        List<Tuple> queryList = jpaQueryFactory.select(
                        qFeed.feedId,
                        qFeed.feedThumnailImage,
                        qFeed.feedLikeList.size(),
                        qFeed.feedCommentList.size()
                ).from(qFeed)
                .where(qFeed.feedId.in(feedIdlist)) // 이 부분을 추가했습니다.
                .fetch();


        List<FeedListDTO> feedlist = new ArrayList<>();
        for(int i = 0; i < queryList.size(); i++){
            FeedListDTO feedListDTO = FeedListDTO.builder()
                    .feedId(queryList.get(i).get(qFeed.feedId))
                    .feedCommentCount((long) queryList.get(i).get(qFeed.feedCommentList.size()))
                    .feedLikeCount((long) queryList.get(i).get(qFeed.feedLikeList.size()))
                    .feedThumnailImage(queryList.get(i).get(qFeed.feedThumnailImage))
                    .build();
            feedlist.add(feedListDTO);
        }

        return feedlist;
    }

    @Override
    public List<Long> selectRandomFeedItem(Long mainFeedLimit,String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFeed qFeed = QFeed.feed;


       List<Feed> feed = jpaQueryFactory.selectFrom(qFeed)
               .where(qFeed.member.memberId.ne(memberId))
                .orderBy(qFeed.feedId.desc())
                .limit(mainFeedLimit).fetch();


        List<Long> list = new ArrayList<>();



       for(int i = 0; i < feed.size(); i++){

           list.add(feed.get(i).getFeedId());
       }



        return list;
    }

    @Override
    public List<FollowDTO> selectRandomFollow(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QFollow qFollow = QFollow.follow;
        QMember qMember = QMember.member;


        List<String> followingMemberIds = jpaQueryFactory.select(qFollow.followMember.memberId)
                .from(qFollow)
                .where(qFollow.followerMember.memberId.eq(memberId))
                .fetch();

        List<Member> query = jpaQueryFactory.selectFrom(qMember)
                .where(qMember.memberId.ne(memberId)
                        .and(qMember.memberId.notIn(followingMemberIds)))
                .limit(5)
                .fetch();



        List<FollowDTO> list = new ArrayList<>();

        for(int i = 0; i<query.size(); i++){
            FollowDTO followDTO = FollowDTO.builder()
                    .memberId(query.get(i).getMemberId())
                    .memberProfileImage(query.get(i).getMemberProfileImage())
                    .followingBackChk(false)
                    .build();

            list.add(followDTO);
        }



        return list;
    }


    @Override
    public FeedListDTO insertFeed(FeedFormDTO feedFormDTO) {


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createFeedTime = now.format(formatter);



        Long articleNo =  feedRepository.findAll().stream().count() + 1;



        List<String> imageFileList =  fileService.uploadFeedImage(feedFormDTO.getFeedImageList(),articleNo);

        for(int i =0; i < imageFileList.size(); i++){
           imageFileList.set(i,fileService.resizeImage(imageFileList.get(i),400,600));
        }


        Member member = Member.builder()
                .memberId(feedFormDTO.getMemberId())
                .build();

        Feed feed = Feed.builder()
                .feedContent(feedFormDTO.getFeedContent())
                .feedCommentList(new ArrayList<>())
                .feedLikeList(new ArrayList<>())
                .feedThumnailImage(imageFileList.get(0))
                .feedDate(createFeedTime)
                .member(member)
                .build();

        feedRepository.save(feed);

        for (String imageFile : imageFileList) {
            FeedImage feedImage = FeedImage.builder()
                    .feed(feed)
                    .feedImage(imageFile)
                    .build();
            // FeedImage 객체 저장
            feedImageRepository.save(feedImage);
        }


        for (String tagList : feedFormDTO.getFeedContentTagList()) {
                FeedTag feedTag = FeedTag.builder()
                        .feed(feed)
                        .feedTagName(tagList)
                        .build();

            // FeedTag 객체 저장
            feedTagRepository.save(feedTag);
        }


        FeedListDTO feedListDTO = FeedListDTO.builder()
                .feedId(feed.getFeedId())
                .feedCommentCount(0L)
                .feedLikeCount(0L)
                .feedThumnailImage(feed.getFeedThumnailImage())
                .build();




        return feedListDTO;
    }
}
