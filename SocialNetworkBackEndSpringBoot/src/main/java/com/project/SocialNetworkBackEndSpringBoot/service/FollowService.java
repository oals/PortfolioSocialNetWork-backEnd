package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.FollowDTO;

import java.util.List;

public interface FollowService {


    boolean followChk(String followMember,String followerMember);
    boolean insertFollowMember(String followMember,String followerMember);

    boolean deleteUnFollowMember(String followMember,String followerMember);

    List<FollowDTO> selectFollowList(String memberId);

    List<FollowDTO> selectFollowerList(String memberId);
}
