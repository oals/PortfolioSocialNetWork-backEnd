package com.project.SocialNetworkBackEndSpringBoot.restController;

import com.project.SocialNetworkBackEndSpringBoot.dto.ChatDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.ChatMessageDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.ChatRoomDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.ChatMessage;
import com.project.SocialNetworkBackEndSpringBoot.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/getChatRoom")
    public List<ChatRoomDTO> getChatRoom(@RequestParam String memberId){


        return chatRoomService.chatRoomList(memberId);

    }

    @PostMapping("/createChatRoom")
    public ChatRoomDTO createChatRoom(@RequestParam String memberId, @RequestParam String memberId2){



        return chatRoomService.createChatRoom(memberId,memberId2);

    }


    @GetMapping("/getChatMessage")
    public ChatDTO getChatMessage(@RequestParam Long chatRoomId, @RequestParam String memberId){


        log.info(chatRoomId);
        log.info(memberId);

        return chatRoomService.selectChatMessage(chatRoomId,memberId);
    }


    @PostMapping("/sendChatMessage")
    public ChatMessageDTO sendChatMessage(@RequestParam Long chatRoomId,
                                          @RequestParam String memberId,
                                          @RequestParam String chatMessage){

        ChatMessageDTO chatMessageDTO =ChatMessageDTO.builder()
                .chatRoomId(chatRoomId)
                .memberId(memberId)
                .chatMessage(chatMessage)
                .build();

        
        
        return chatRoomService.insertChatMessage(chatMessageDTO);
    }


}
