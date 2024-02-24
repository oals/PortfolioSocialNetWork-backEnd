
# PortfolioLibrary
소셜 네트워크 웹 사이트 개인 프로젝트 포트폴리오

# 소개
인스타그램의 디자인과 기능을 모방한 사이트 입니다. <br>
피드를 작성 할 수 있고 좋아요, 댓글 작성, 검색 , 실시간 알림, 메세지 기능을 구현한 사이트 입니다.

백엔드는 스프링부트를 사용 하였고 <br>
프론트엔드는 리엑트를 사용하여 개발했습니다.

프론트엔드 코드 주소 : https://github.com/oals/PortfolioSocialNetWork-frontEnd


# 제작기간 & 참여 인원
<UL>
  <LI>2024.02.05 ~ 2024.02.22</LI>
  <LI>개인 프로젝트</LI>
</UL>


# 사용기술
![js](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![js](https://img.shields.io/badge/Java-FF0000?style=for-the-badge&logo=Java&logoColor=white)
![js](https://img.shields.io/badge/IntelliJ-004088?style=for-the-badge&logo=IntelliJ&logoColor=white)
![js](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white)
![js](https://img.shields.io/badge/security-6DB33F?style=for-the-badge&logo=security&logoColor=white)

![js](https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
![js](https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![js](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)

![js](https://img.shields.io/badge/React-0769AD?style=for-the-badge&logo=React&logoColor=white)


# E-R 다이어그램

![erd 편집](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/b4cfe8ed-cc24-40ff-baf6-abe7a57b2b43)


<br>
<br>

# Entity

<details> 
 <summary> Member Entity 
 
 </summary> 



    @Entity
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Member {

    @Id
    private String memberId;

    private String memberPswd;
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberAddress;
    private String memberDate;

    private String memberJobTitle;
    private String memberSchoolName;

    private String memberProfileImage;
    private String createDate;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static Member createMember(MemberDTO memberDTO){
        Member member = new Member();
        member.setMemberId(memberDTO.getMemberId());
        member.setMemberName(memberDTO.getMemberName());
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberPhone(memberDTO.getMemberPhone());
        member.setMemberDate(memberDTO.getMemberDate());
        member.setMemberAddress(memberDTO.getMemberAddress());
        member.setMemberJobTitle(memberDTO.getMemberJobTitle());
        member.setMemberSchoolName(memberDTO.getMemberSchoolName());
        member.setMemberProfileImage("c:/SocialNetwork/normalProfileImage.jpg");
        member.setCreateDate(LocalDateTime.now().toString());
        member.setRole(Role.USER); //일반 유저 디폴트값 5등급

        // 암호화
        member.setMemberPswd(memberDTO.getMemberPswd());

        return member;
    }

    }

 
</details>


<details>
 <summary> Feed Entity
 
 </summary> 



    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Feed {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    private String feedContent;

    private String feedDate;

    private String feedThumnailImage;

    @OneToMany(mappedBy = "feed")
    private List<FeedComment> feedCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<FeedImage> feedImageList = new ArrayList<>();


    @OneToMany(mappedBy = "feed")
    private List<FeedTag> feedTagList = new ArrayList<>();



    @OneToMany(mappedBy = "feed")
    private List<FeedLike> feedLikeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;



    }



 
</details>


<details>
 <summary> FeedComment Entity
 
 </summary> 




    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class FeedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentId;

    private String feedCommentContent;

    private String feedCommentDate;

    private Long feedCommentParent; //부모 댓글 번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id")
    private Feed feed; //피드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; //댓글 작성자


    }

 
 
</details>


<details>
 <summary> FeedImage Entity
 
 </summary> 



    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class FeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedImageNo;

    private String feedImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id")
    private Feed feed;


    }


 
</details>


<details>
 <summary> FeedLike Entity
 
 </summary> 



    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class FeedLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedLikeId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    }


 
</details>


<details>
 <summary> FeedTag Entity
 
 </summary> 




    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class FeedTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedTagId;

    private String feedTagName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id")
    private Feed feed;



    }


 
</details>


<details>
 <summary> Follow Entity
 
 </summary> 



    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_member_id", referencedColumnName = "memberId")
    private Member followMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_member_id", referencedColumnName = "memberId")
    private Member followerMember;

    }



 
</details>


<details>
 <summary> ChatRoom Entity
 
 </summary> 



    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class ChatRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private String LastUseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_member_id", referencedColumnName = "memberId")
    private Member chatMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_member_id2", referencedColumnName = "memberId")
    private Member chatMemberId2;


    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessageList = new ArrayList<>();


    public void updateLastUseDate(String useDate){
        this.LastUseDate = useDate;
    }

    }



 
</details>


<details>
 <summary> ChatMessage Entity
 
 </summary> 



    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageNo;

    private String chatMessage;

    private String chatMessageDate;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;






    }


 
</details>


<details>
 <summary> Notification Entity
 
 </summary> 





    @Entity
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationNo;

    private int notificationMessageType;

    private boolean viewChk;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "comment_feed_id", referencedColumnName = "feedId")
    private Feed feedComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_feed_id", referencedColumnName = "feedId")
    private Feed feedLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_member_id", referencedColumnName = "memberId")
    private Member follow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "read_member_id", referencedColumnName = "memberId")
    private Member readMember;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_member_id", referencedColumnName = "memberId")
    private Member sendMember;



    public void viewChkFunction(){
      this.viewChk = true;
    }




    }




 
</details>





# 핵심 기능 및 페이지 소개


<br>
<br>




<h3>로그인 및 메인 페이지</h3>
<br>

![freecompress-로그인 메인](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/f2be8543-44ea-4d3c-a073-4168f3f2b76f)


<br>
<br>

<details>
 <summary> 
 
 </summary> 
 
</details>



<UL>

 <LI>최신 피드를 감상 할 수 있습니다.</LI>
 <li>팔로우 관계가 아닌 사용자들을 팔로우 추천 탭에 나타나도록 구현했습니다.</li>
</UL>



<br>
<br>




<h3>프로필 변경 </h3>
<br>

![프로필 변경](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/1f4ee25c-efb8-43f4-8b68-b2b0256ed4bc)


<br>
<br>

<details>
 <summary> 
 
 </summary> 
 
</details>

<details>
 <summary> 프로필 변경 서비스 
 
 </summary> 




    @Override
    public String updateMemberProfileImage(MemberProfileUpdateDTO memberProfileUpdateDTO) {

        Member member = memberRepository.findById(memberProfileUpdateDTO.getMemberId()).orElseThrow();

        member.setMemberId(memberProfileUpdateDTO.getMemberId());
        member.setMemberSchoolName(memberProfileUpdateDTO.getMemberSchoolName());
        member.setMemberJobTitle(memberProfileUpdateDTO.getMemberJobTitle());
        member.setMemberName(memberProfileUpdateDTO.getMemberName());

        if(memberProfileUpdateDTO.getMemberProfileImage() != null) {

            if(!member.getMemberProfileImage().equals("c:/SocialNetwork/normalProfileImage.jpg")) {
                fileService.deleteFile(member.getMemberProfileImage());
                fileService.deleteFile(member.getMemberProfileImage().replace("Resized", ""));
            }

            String profileImagePath = fileService.uploadMemberProfileImage(memberProfileUpdateDTO.getMemberId(), memberProfileUpdateDTO.getMemberProfileImage());
            String resizeProfileImagePath = fileService.resizeImage(profileImagePath, 200, 200);
            member.setMemberProfileImage(resizeProfileImagePath);
        }

        memberRepository.save(member);

        return member.getMemberProfileImage();
    }





 
 
</details>



<details>
 <summary> 업로드 서비스
 
 </summary> 




      @Override
      public String uploadMemberProfileImage(String memberId, MultipartFile profileImage) {

        String path = itemImgLocation + "/Member/" + memberId;  

        //게시글 폴더 생성
        createDirectoryService.CreateDebatePostImageFolder(path);    


        UUID uuid = UUID.randomUUID();

        String mimeType = profileImage.getContentType();
        String extension = mimeType.split("/")[1]; // "png", "jpeg" 등
        String savedFileName = uuid + "." + extension;
        String fileUploadFullUrl = path + "/"+ savedFileName;
        // 결과 : 업로드할 경로 + / + uuid값 + .파일확장자
        try {
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(profileImage.getBytes());
            fos.close();
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        

        return fileUploadFullUrl;
    }


 
</details>




<UL>

<li>프로필 이미지,이름,직업,학교 의 정보를 변경 할 수 있도록 구현했습니다 </li>
<li>프로필 이미지 변경 시 C드라이브의 해당 멤버 프로필 폴더의 이미지를 교체 하도록 구현했습니다. </li>
</UL>

<br>
<br>




<h3>업로드</h3>
<br>

![업로드](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/789c0437-15f2-41b8-b07e-a28b454b9cb3)

<br>
<br>

<details>
 <summary> 
 
 </summary> 
 
</details>

<details>
 <summary> 업로드 서비스
 
 </summary> 



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


    

 
</details>



<details>
 <summary> 이미지 업로드 서비스
 
 </summary> 



    @Override
    public List<String> uploadFeedImage(List<MultipartFile> list,Long articleNo) {



        List<String> imageFileUrlList = new ArrayList<>();


        if(list.size() != 0) {   //해당 글 내용에 이미지가 있으면

            //이미지 저장 폴더 경로
            String path = itemImgLocation + "/Feed/" + articleNo;  

            //게시글 폴더 생성
            createDirectoryService.CreateDebatePostImageFolder(path);   

            //이미지 파일 업로드
            for(int i = 0; i <  list.size(); i++) {

                //이미지 파일 업로드
                if(!list.get(i).getOriginalFilename().isEmpty()){

                    UUID uuid = UUID.randomUUID();

                    String savedFileName = uuid + list.get(i).getOriginalFilename(); // 난수에 확장자 붙이기
                    String fileUploadFullUrl = path + "/"+ savedFileName;
                    // 결과 : 업로드할 경로 + / + uuid값 + .파일확장자
                    try {
                        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
                        fos.write(list.get(i).getBytes());
                        fos.close();
                    }catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                    imageFileUrlList.add(fileUploadFullUrl);

                }else{
                    imageFileUrlList.add(null);
                }
            }
        }





        //업로드 완료
        return imageFileUrlList;


      }


 
</details>



<UL>

<li> 여러 이미지를 업로드 할 수 있고 게시물의 내용과 태그를 구분해서 입력 받도록 구현 했습니다.</li>
<li>피드의 태그를 엔티티로 따로 관리 함으로써 태그를 통한 검색 시 데이터를 쉽게 가져올 수 있도록 구현했습니다. </li>
</UL>


<br>
<br>




<h3>좋야요 및 댓글 작성</h3>
<br>

![freecompress-좋아요 댓글](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/e801e413-3ffd-4e2e-88ac-886b0d4460d6)

<br>
<br>

<details>
 <summary> 중복 좋아요 검사 서비스
 
 </summary> 


   @Override
    public boolean FeedLikeChk(Long feedId, String memberId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QFeedLike qFeedLike = QFeedLike.feedLike;


        boolean feedLikeChk = queryFactory.selectFrom(qFeedLike)
                .where(qFeedLike.member.memberId.eq(memberId).and(qFeedLike.feed.feedId.eq(feedId)))
                .fetch().isEmpty();


        return feedLikeChk;
    }

    
 
</details>


<details>
 <summary> 좋아요 서비스
 
 </summary> 



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
    
 
</details>




<details>
 <summary> 댓글 작성 서비스
 
 </summary> 




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


            if(viewChk) { //실시간 알림 생성
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


    
 
</details>






<UL>

<li>피드 좋아요를 엔티티로 관리함으로 써 중복 좋아요 클릭을 방지하였습니다.</li>

</UL>


<br>
<br>




<h3>태그 및 검색 </h3>
<br>

![freecompress-태그 및 검색](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/1bfcef8f-e9a0-4aca-8616-8a4a664b9420)


<br>
<br>

<details>
 <summary> 
 
 </summary> 
 
</details>


<details>
 <summary> 검색 서비스
 
 </summary> 



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
                .where(qFeed.feedId.in(feedIdlist)) 
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

    
 
</details>




<UL>

<li>태그 클릭 및 태그 검색 시 관련된 피드들을 가져오도록 구현했습니다. </li>
</UL>


<br>
<br>




<h3>팔로우 및 언팔로우 </h3>
<br>

![팔로우](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/d95f15a4-f25a-4a9a-bf1e-a7589a25a0de)


<br>
<br>

<details>
 <summary> 
 
 </summary> 
 
</details>



<details>
 <summary> 팔로우 서비스
 
 </summary> 




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


            if(viewChk) {//실시간 알림 생성

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

    
 
</details>





<details>
 <summary> 언팔로우 서비스
 
 </summary> 
 
</details>



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

    



<UL>

<li>유저간의 팔로우 팔로워 관계를 구현했습니다. </li>
<li>팔로우 혹은 맞팔 관계일 때 메세지를 보낼 수 있도록 구현했습니다.</li>
</UL>


<br>
<br>




<h3>알림</h3>
<br>

![알림 및 메세지 답장](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/9501bc14-0e48-40cf-83be-a632f27ea45b)

<br>
<br>



</details>



<details>
 <summary> 
 
 </summary> 
 
</details>



<details>
 <summary> 알림 서비스
 
 </summary> 


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


 
 
</details>


<details>
 <summary> 실시간 알림 전송 소켓 코드
 
 </summary> 



    @Component
    @Log4j2
    public class SimpleTextHandler extends TextWebSocketHandler {
    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received message123: " + message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    public void sendMessageToAll(String sendMemberId, String readMemberId) throws Exception {
        log.info(sessions);

        for (WebSocketSession session : sessions.values()) {
            JsonObject jsonMessage = new JsonObject();
            jsonMessage.addProperty("sendMemberId", sendMemberId);
            jsonMessage.addProperty("readMemberId", readMemberId);

            session.sendMessage(new TextMessage(jsonMessage.toString()));
        }
     }  
   }

</details>
 

<UL>

<li> 댓글 작성, 좋아요 , 팔로우 , DM 의 이벤트가 있을 때 소켓을 통해 실시간 알림을 전송 받도록 구현했습니다. </li>
<li> 이미 읽은 알림은 text-secondary 속성을 통해 흐리게 만들고 새로운 알림은 fw-bold를 통해 진하게 표시되도록 구현했습니다. </li>
</UL>


<br>
<br>




<h3> 채팅</h3>
<br>

![메세지 보내기](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/c8a05319-0a39-44aa-855f-0f92f0ae4279)


![채팅 대화](https://github.com/oals/PortfolioSocialNetWork-backEnd/assets/136543676/5ef152b2-da72-4ded-9bca-69893704cdc4)



<br>
<br>

<details>
 <summary> 채팅방 생성 서비스
 
 </summary> 



     @Override
     public ChatRoomDTO createChatRoom(String memberId, String memberId2) {


        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QChatRoom qChatRoom = QChatRoom.chatRoom;

        boolean chatRoomChk = jpaQueryFactory.selectFrom(qChatRoom)
                .where(qChatRoom.chatMemberId.memberId.eq(memberId)
                        .and(qChatRoom.chatMemberId2.memberId.eq(memberId2))
                        .or(qChatRoom.chatMemberId.memberId.eq(memberId2)
                                .and(qChatRoom.chatMemberId2.memberId.eq(memberId))))
                .fetch().stream().count() == 0;


        if(chatRoomChk){

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createChatRoom = now.format(formatter);


            try{
                Member member = memberRepository.findById(memberId).orElseThrow();

                Member member2 = memberRepository.findById(memberId2).orElseThrow();


                ChatRoom chatRoom = ChatRoom.builder()
                        .chatMemberId(member)
                        .chatMemberId2(member2)
                        .chatMessageList(new ArrayList<>())
                        .LastUseDate(createChatRoom)
                        .build();

                chatRoomRepository.save(chatRoom);


                ChatRoomDTO newChatRoomDTO = new ChatRoomDTO();

                newChatRoomDTO.setChatRoomId(chatRoom.getChatRoomId());
                if(memberId.equals(chatRoom.getChatMemberId().getMemberId())){
                    newChatRoomDTO.setMemberId(member2.getMemberId());
                    newChatRoomDTO.setMemberProfileImage(member2.getMemberProfileImage());
                }else{
                    newChatRoomDTO.setMemberId(member.getMemberId());
                    newChatRoomDTO.setMemberProfileImage(member.getMemberProfileImage());
                }


                return newChatRoomDTO;

            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }else{
            return null;
        }



      }


 
</details>


<details>
 <summary> 채팅 메세지 조회 서비스
 
 </summary> 



    @Override
    public ChatDTO selectChatMessage(Long chatRoomId, String memberId) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setChatRoomId(chatRoomId);

        if(memberId.equals(chatRoom.getChatMemberId().getMemberId())){
            chatDTO.setMemberId(chatRoom.getChatMemberId2().getMemberId());
            chatDTO.setMemberProfileImage(chatRoom.getChatMemberId2().getMemberProfileImage());
        }else{
            chatDTO.setMemberId(chatRoom.getChatMemberId().getMemberId());
            chatDTO.setMemberProfileImage(chatRoom.getChatMemberId().getMemberProfileImage());
        }


        List<ChatMessageDTO> chatMessageDTOS = new ArrayList<>();

        for(int i = 0; i < chatRoom.getChatMessageList().size(); i++){
            ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                    .chatMessageNo(chatRoom.getChatMessageList().get(i).getChatMessageNo())
                    .chatMessage(chatRoom.getChatMessageList().get(i).getChatMessage())
                    .chatMessageDate(chatRoom.getChatMessageList().get(i).getChatMessageDate())
                    .memberId(chatRoom.getChatMessageList().get(i).getMember().getMemberId())
                    .build();

            chatMessageDTOS.add(chatMessageDTO);
        }

        chatDTO.setChatMessageDTOList(chatMessageDTOS);



        return chatDTO;
      }



 
 
</details>



<details>
 <summary> 채팅 메세지 전송 서비스
 
 </summary> 



    @Override
    public ChatMessageDTO insertChatMessage(ChatMessageDTO chatMessageDTO) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createChatMessage = now.format(formatter);


        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDTO.getChatRoomId()).orElseThrow();

        Member member = Member.builder()
                .memberId(chatMessageDTO.getMemberId())
                .build();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QNotification qNotification = QNotification.notification;

        boolean viewChk = jpaQueryFactory.selectFrom(qNotification)
                .where(qNotification.chatRoom.chatRoomId.eq(chatRoom.getChatRoomId())
                        .and(qNotification.viewChk.isFalse()))
                .fetch().stream().count() == 0;

        if (viewChk) {

            String readMemberId = "";
            if(member.getMemberId().equals(chatRoom.getChatMemberId().getMemberId())){
                readMemberId = chatRoom.getChatMemberId2().getMemberId();
            }else{
                readMemberId = chatRoom.getChatMemberId().getMemberId();
            }


            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .sendMemberId(member.getMemberId())
                    .readMemberId(readMemberId)
                    .notificationMessageType(4)
                    .chatRoomId(chatRoom.getChatRoomId())
                    .build();

            notificationService.createNotification(notificationDTO);
        }


        chatRoom.updateLastUseDate(createChatMessage); //채팅방 사용시간 업데이트
        chatRoomRepository.save(chatRoom);

        ChatMessage chatMessage = ChatMessage.builder()
                .chatMessage(chatMessageDTO.getChatMessage())
                .chatMessageDate(createChatMessage)
                .chatRoom(chatRoom)
                .member(member)
                .build();

        chatMessageRepository.save(chatMessage);


        ChatMessageDTO newChatMessageDTO = ChatMessageDTO.builder()
                .chatMessageNo(chatMessage.getChatMessageNo())
                .chatMessageNo(chatMessage.getChatMessageNo())
                .chatMessage(chatMessage.getChatMessage())
                .memberId(chatMessage.getMember().getMemberId())
                .build();



        return newChatMessageDTO;
    }


 
</details>



<UL>

<li>제일 마지막으로 사용한 채팅방이 상단에 나타나도록 구현했습니다. </li>
<li>팔로우 혹은 팔로워를 더블 클릭 시 채팅방이 생성되도록 구현했습니다.</li>

</UL>


# 프로젝트를 통해 느낀 점과 소감










