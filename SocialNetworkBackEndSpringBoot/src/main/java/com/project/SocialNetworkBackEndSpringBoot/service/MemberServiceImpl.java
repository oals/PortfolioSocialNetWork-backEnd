package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.MemberDTO;
import com.project.SocialNetworkBackEndSpringBoot.dto.MemberProfileUpdateDTO;
import com.project.SocialNetworkBackEndSpringBoot.entity.Member;
import com.project.SocialNetworkBackEndSpringBoot.entity.QMember;
import com.project.SocialNetworkBackEndSpringBoot.repository.MemberRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

    @PersistenceContext
    EntityManager em;

    private final FileService fileService;
    private final MemberRepository memberRepository;
    @Override
    public boolean memberRegister(MemberDTO memberDTO) {

        try{
            Member member= Member.createMember(memberDTO);
            memberRepository.save(member);

            return true;
        }catch(Exception e){

            return false;
        }

    }

    @Override
    public boolean memberLogin(MemberDTO memberDTO) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember qMember = QMember.member;

        boolean chk = queryFactory.selectFrom(qMember)
                .where(qMember.memberId.eq(memberDTO.getMemberId())
                        .and(qMember.memberPswd.eq(memberDTO.getMemberPswd())))
                .fetchOne() != null;



        return chk;
    }

    @Override
    public String updateMemberProfileImage(MemberProfileUpdateDTO memberProfileUpdateDTO) {

        Member member = memberRepository.findById(memberProfileUpdateDTO.getMemberId()).orElseThrow();

        member.setMemberId(memberProfileUpdateDTO.getMemberId());
        member.setMemberSchoolName(memberProfileUpdateDTO.getMemberSchoolName());
        member.setMemberJobTitle(memberProfileUpdateDTO.getMemberJobTitle());
        member.setMemberName(memberProfileUpdateDTO.getMemberName());

        if(memberProfileUpdateDTO.getMemberProfileImage() != null) {

            if(!member.getMemberProfileImage().equals("c:/SocialNetwork/normalProfileImage.jpg")) {
                fileService.deleteFile(member.getMemberProfileImage());
                fileService.deleteFile(member.getMemberProfileImage().replace("Resized", ""));
            }

            String profileImagePath = fileService.uploadMemberProfileImage(memberProfileUpdateDTO.getMemberId(), memberProfileUpdateDTO.getMemberProfileImage());
            String resizeProfileImagePath = fileService.resizeImage(profileImagePath, 200, 200);
            member.setMemberProfileImage(resizeProfileImagePath);
        }

        memberRepository.save(member);

        return member.getMemberProfileImage();
    }












}
