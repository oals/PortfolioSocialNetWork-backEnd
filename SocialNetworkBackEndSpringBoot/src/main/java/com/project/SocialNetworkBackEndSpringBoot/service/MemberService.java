package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.MemberDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.MemberProfileUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    boolean memberRegister(MemberDTO memberDTO);

    boolean memberLogin(MemberDTO memberDTO);


    String updateMemberProfileImage(MemberProfileUpdateDTO memberProfileUpdateDTO);
}
