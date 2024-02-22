package com.project.SocialNetworkBackEndSpringBoot.restController;

import com.project.SocialNetworkBackEndSpringBoot.dto.MemberDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.MemberProfileUpdateDTO;
import com.project.SocialNetworkBackEndSpringBoot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/register")
    public boolean register(@RequestBody MemberDTO memberDTO){

        return memberService.memberRegister(memberDTO);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody MemberDTO memberDTO){


        return memberService.memberLogin(memberDTO);
    }


    @PutMapping("/updateProfileImage")
    public String updateProfileImage(
            @RequestParam("memberId") String memberId,
            @RequestParam("memberName") String memberName,
            @RequestParam("memberJobTitle") String memberJobTitle,
            @RequestParam("memberSchoolName") String memberSchoolName,
            @RequestParam(value = "memberProfileImage", required = false) MultipartFile memberProfileImage
    ) {
        MemberProfileUpdateDTO memberProfileUpdateDTO = new MemberProfileUpdateDTO();
        memberProfileUpdateDTO.setMemberId(memberId);
        memberProfileUpdateDTO.setMemberName(memberName);
        memberProfileUpdateDTO.setMemberJobTitle(memberJobTitle);
        memberProfileUpdateDTO.setMemberSchoolName(memberSchoolName);

        if (memberProfileImage != null) {
            memberProfileUpdateDTO.setMemberProfileImage(memberProfileImage);
        }


        return  memberService.updateMemberProfileImage(memberProfileUpdateDTO);
    }



}
