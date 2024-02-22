package com.project.SocialNetworkBackEndSpringBoot.service;


import com.project.SocialNetworkBackEndSpringBoot.dto.ChatDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.ChatMessageDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.ChatRoomDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.*;
import com.project.SocialNetworkBackEndSpringBoot.repository.ChatMessageRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.ChatRoomRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.MemberRepository;
import com.project.SocialNetworkBackEndSpringBoot.repository.NotificationRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatRoomServiceImpl implements ChatRoomService{

    @PersistenceContext
    EntityManager em;

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final NotificationService notificationService;

    private final NotificationRepository notificationRepository;
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

    @Override
    public List<ChatRoomDTO> chatRoomList(String memberId) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QChatRoom qChatRoom = QChatRoom.chatRoom;

        List<ChatRoom> query = jpaQueryFactory.selectFrom(qChatRoom)
                .where(qChatRoom.chatMemberId.memberId.eq(memberId)
                        .or(qChatRoom.chatMemberId2.memberId.eq(memberId)))
                .orderBy(qChatRoom.LastUseDate.desc())
                .fetch();


        List<ChatRoomDTO> chatRoomDTOS = new ArrayList<>();

        for(int i = 0; i < query.size(); i++){

            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            chatRoomDTO.setChatRoomId(query.get(i).getChatRoomId());
            if(memberId.equals(query.get(i).getChatMemberId().getMemberId())){
                chatRoomDTO.setMemberId(query.get(i).getChatMemberId2().getMemberId());
                chatRoomDTO.setMemberProfileImage(query.get(i).getChatMemberId2().getMemberProfileImage());
            }else{
                chatRoomDTO.setMemberId(query.get(i).getChatMemberId().getMemberId());
                chatRoomDTO.setMemberProfileImage(query.get(i).getChatMemberId().getMemberProfileImage());
            }


            chatRoomDTOS.add(chatRoomDTO);

        }




        return chatRoomDTOS;
    }

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
}
