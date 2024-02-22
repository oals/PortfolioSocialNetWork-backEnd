package com.project.SocialNetworkBackEndSpringBoot.entity;


import com.project.SocialNetworkBackEndSpringBoot.config.Role;
import com.project.SocialNetworkBackEndSpringBoot.dto.MemberDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private String memberId;

    private String memberPswd;
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberAddress;
    private String memberDate;

    private String memberJobTitle;
    private String memberSchoolName;

    private String memberProfileImage;
    private String createDate;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static Member createMember(MemberDTO memberDTO){
        Member member = new Member();
        member.setMemberId(memberDTO.getMemberId());
        member.setMemberName(memberDTO.getMemberName());
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberPhone(memberDTO.getMemberPhone());
        member.setMemberDate(memberDTO.getMemberDate());
        member.setMemberAddress(memberDTO.getMemberAddress());
        member.setMemberJobTitle(memberDTO.getMemberJobTitle());
        member.setMemberSchoolName(memberDTO.getMemberSchoolName());
        member.setMemberProfileImage("c:/SocialNetwork/normalProfileImage.jpg");
        member.setCreateDate(LocalDateTime.now().toString());
        member.setRole(Role.USER); //일반 유저 디폴트값 5등급

        // 암호화
        member.setMemberPswd(memberDTO.getMemberPswd());

        return member;
    }

}
