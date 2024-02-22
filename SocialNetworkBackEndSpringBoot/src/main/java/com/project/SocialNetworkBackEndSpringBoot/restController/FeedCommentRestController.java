package com.project.SocialNetworkBackEndSpringBoot.restController;

import com.project.SocialNetworkBackEndSpringBoot.dto.FeedCommentDTO;
import com.project.SocialNetworkBackEndSpringBoot.service.FeedCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class FeedCommentRestController {


    private final FeedCommentService feedCommentService;

    @PostMapping("/uploadFeedComment")
    public FeedCommentDTO uploadFeedComment(@RequestBody FeedCommentDTO feedCommentDTO){

        log.info(feedCommentDTO);

        return feedCommentService.insertFeedComment(feedCommentDTO);
    }




}
