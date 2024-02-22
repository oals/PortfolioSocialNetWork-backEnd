package com.project.SocialNetworkBackEndSpringBoot.restController;


import com.project.SocialNetworkBackEndSpringBoot.dto.FeedItemInfoDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.FollowDTO;
import com.project.SocialNetworkBackEndSpringBoot.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class MainRestController {

    private final FeedService feedService;

    @GetMapping("/mainFeed")
    public List<Long> mainFeed(@RequestParam Long mainFeedLimit,@RequestParam String memberId){



        return feedService.selectRandomFeedItem(mainFeedLimit,memberId);
    }

    @GetMapping("/mainFollow")
    public List<FollowDTO> mainFollow(@RequestParam String memberId){


        return feedService.selectRandomFollow(memberId);
    }


}
