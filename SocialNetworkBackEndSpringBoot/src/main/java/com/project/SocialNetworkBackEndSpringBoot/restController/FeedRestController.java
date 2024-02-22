package com.project.SocialNetworkBackEndSpringBoot.restController;

import com.project.SocialNetworkBackEndSpringBoot.dto.FeedFormDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.FeedInfoDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.FeedItemInfoDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.FeedListDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.FeedLike;
import com.project.SocialNetworkBackEndSpringBoot.service.FeedLikeService;
import com.project.SocialNetworkBackEndSpringBoot.service.FeedService;
import com.project.SocialNetworkBackEndSpringBoot.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class FeedRestController {


    private final FeedService feedService;
    private final FollowService followService;

    private final FeedLikeService feedLikeService;

    @GetMapping("/getFeedInfo")
    public FeedInfoDTO getFeedInfo(@RequestParam String target,@RequestParam String memberId){

        FeedInfoDTO feedInfoDTO = feedService.selectFeedInfo(target);
        feedInfoDTO.setFollowChk(followService.followChk(target,memberId));

        log.info("팔로우 체크 : " + feedInfoDTO.isFollowChk());

        return feedInfoDTO;
    }

    @GetMapping("/searchTag")
    public List<FeedListDTO> searchTag(@RequestParam String tagName){


        return feedService.selectTagFeed(tagName);
    }

    @GetMapping("/getFeedItemInfo")
    public FeedItemInfoDTO getFeedItemInfo(@RequestParam Long feedKey,
                                            @RequestParam String memberId){

        FeedItemInfoDTO feedItemInfoDTO = feedService.selectFeedItemInfo(feedKey);

        boolean likeChk = feedLikeService.FeedLikeChk(feedKey,memberId);
        feedItemInfoDTO.setFeedLikeChK(!likeChk);

        return feedItemInfoDTO;
    }

    @PostMapping("/FeedLike")
    public boolean FeedLike(@RequestBody Map<String, String> data){

        return feedLikeService.insertFeedLike(Long.parseLong(data.get("feedId")),data.get("memberId"));
    }

    @DeleteMapping("delFeedLike")
    public boolean delFeedLike(@RequestBody Map<String, String> data){

        return feedLikeService.deleteFeedLike(Long.parseLong(data.get("feedId")),data.get("memberId"));
    }



    @PostMapping("/uploadFeed")
    public FeedListDTO uploadFeed(@RequestParam("memberId") String memberId,
                                  @RequestParam("feedContent") String feedContent,
                                  @RequestParam("feedContentTag") String feedContentTag,
                                  @RequestParam("imageFiles") MultipartFile[] imageFiles) {


        List<String> tagList = Arrays.stream(feedContentTag.split("#"))
                .skip(1)
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
                .collect(Collectors.toList());



        FeedFormDTO feedFormDTO = FeedFormDTO.builder()
                .memberId(memberId)
                .feedContent(feedContent)
                .feedContentTagList(tagList)
                .feedImageList(Arrays.stream(imageFiles).toList())
                .build();



        return feedService.insertFeed(feedFormDTO);
    }




}
