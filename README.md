
# PortfolioLibrary
소셜 네트워크 웹 사이트 개인 프로젝트 포트폴리오

# 소개
인스타그램의 디자인과 기능을 모방한 사이트 입니다. <br>
피드를 작성 할 수 있고 좋아요, 댓글 작성, 검색 , 실시간 알림, 메세지 기능을 구현한 사이트 입니다.

백엔드는 스프링부트를 사용하였고 <br>
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
 <summary> 
 
 </summary> 
 
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

<details>
 <summary> 
 
 </summary> 
 
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
 <summary> 
 
 </summary> 
 
</details>

<UL>

<li>제일 마지막으로 사용한 채팅방이 상단에 나타나도록 구현했습니다. </li>
<li>팔로우 혹은 팔로워를 더블 클릭 시 채팅방이 생성되도록 구현했습니다.</li>

</UL>


# 프로젝트를 통해 느낀 점과 소감










