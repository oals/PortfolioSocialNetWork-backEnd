package com.project.SocialNetworkBackEndSpringBoot.restController;


import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import com.project.SocialNetworkBackEndSpringBoot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class NotificationRestController {

    private final NotificationService notificationService;


    @GetMapping("myNotification")
    public List<NotificationDTO> myNotification(@RequestParam String memberId){




        return  notificationService.selectMyNotification(memberId);
    }

    @PutMapping("/notificationViewUpdate")
    public void notificationViewUpdate(@RequestBody Map<String,String> data){

        notificationService.notificationViewUpdate(data.get("memberId"));

    }

    @GetMapping("/notificationChk")
    public boolean notificationChk(@RequestParam String memberId){

        log.info("호출 체크 : " + memberId);

        return notificationService.notificationViewChk(memberId);
    }
}
