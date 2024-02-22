package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {


    boolean createNotification(NotificationDTO notificationDTO);

    List<NotificationDTO> selectMyNotification(String memberId);

    boolean notificationViewChk(String memberId);

    void notificationViewUpdate(String memberId);
}
