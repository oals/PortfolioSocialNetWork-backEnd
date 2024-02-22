package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.FeedFormDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<String> uploadFeedImage(List<MultipartFile> list,Long articleNo);

    String uploadMemberProfileImage(String memberId,MultipartFile profileImage);

    void deleteFolder(String delPath);


    void deleteFile(String delPath);

    String resizeImage(String originalImagePath, int width, int height);



}
