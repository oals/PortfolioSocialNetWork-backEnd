package com.project.SocialNetworkBackEndSpringBoot.restController;

import com.project.SocialNetworkBackEndSpringBoot.dto.FollowDTO;
import com.project.SocialNetworkBackEndSpringBoot.repository.FollowRepository;
import com.project.SocialNetworkBackEndSpringBoot.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class FollowRestController {


    private final FollowService followService;
    @PostMapping("/followMember")
    public boolean followMember(@RequestBody Map<String, String> data){


        return followService.insertFollowMember(data.get("followMember"),data.get("followerMember"));
    }

    @DeleteMapping("unFollowMember")
    public boolean unFollowMember(@RequestBody Map<String, String> data){

        return followService.deleteUnFollowMember(data.get("followMember"),data.get("followerMember"));

    }


    @GetMapping("/followList")
    public List<FollowDTO> followList(@RequestParam String target){

        log.info("target");
        return followService.selectFollowList(target);
    }

    @GetMapping("/followerList")
    public List<FollowDTO> followerList(@RequestParam String target){
        return followService.selectFollowerList(target);

    }



}
