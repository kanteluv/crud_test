package com.sparta.crudtest.service;

import com.sparta.crudtest.dto.*;
import com.sparta.crudtest.entity.*;
import com.sparta.crudtest.repository.MemberCommentRepository;
import com.sparta.crudtest.repository.MemberRepository;
import com.sparta.crudtest.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final MemberCommentRepository memberCommentRepository;

    @Transactional
    public Member findMember(Long id){
        return memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 선수 입니다.")
        );
    }

    @Transactional(readOnly = true)
    public List<MemberCommentDto> getAllMemberComment(){
        List<MemberComment> memberCommentList = memberCommentRepository.findAll();
        List<MemberCommentDto> memberCommentDtoList = new ArrayList<>();

        for(MemberComment memberComment: memberCommentList) {
            MemberCommentDto memberCommentDto = new MemberCommentDto(memberComment);
            memberCommentDtoList.add(memberCommentDto);
        }

        return memberCommentDtoList;
    }

    @Transactional(readOnly = true)
    public List<MemberDto> getMembers(){
        List<Member> memberList = memberRepository.findAll();
        List<MemberCommentDto> memberCommentDtoList = getAllMemberComment();
        List<MemberDto> memberDtoList = new ArrayList<>();


        for(Member member: memberList) {
            MemberDto memberDto = new MemberDto(member);

            List<MemberCommentDto> memberCommentAtMemberIdDto = memberCommentDtoList.stream()
                    .filter(a -> Objects.equals(a.getId(), memberDto.getId()))
                    .collect(Collectors.toList());

            memberDto.setMemberCommentDtoList(memberCommentAtMemberIdDto);

            memberDtoList.add(memberDto);
        }

        return memberDtoList;
    }


    @Transactional
    public MemberResponseDto createMember(Long id, MemberRequestDto memberRequestDto, User user) {
        Team team = teamRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 팀 입니다.")
        );
        Member member = new Member(memberRequestDto, team);
        member.setUserName(user.getUsername());
        memberRepository.save(member);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), member.getUserName(), member.getMemberName(), member.getMemberContent());
        memberResponseDto.setCreatedTime(member.getCreatedAt());
        memberResponseDto.setTeam_id(id);
        return memberResponseDto;
    }

    @Transactional
    public MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto, User user){
        try {
            Member member = findMember(id);
            UserRoleEnum userRoleEnum = user.getRole();

                switch (userRoleEnum) {
                    case USER:
                        if (StringUtils.equals(member.getUserName(), user.getUsername())) {
                            member.update(memberRequestDto);
                            MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), member.getUserName(), member.getMemberName(), member.getMemberContent());
                            memberResponseDto.setModifiedTime(member.getModifiedAt());
                            memberResponseDto.setTeam_id(member.getTeam().getId());
                            return memberResponseDto;
                        }
                    case ADMIN:
                        member.update(memberRequestDto);
                        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), member.getUserName(), member.getMemberName(), member.getMemberContent());
                        memberResponseDto.setModifiedTime(member.getModifiedAt());
                        memberResponseDto.setTeam_id(member.getTeam().getId());
                        return memberResponseDto;
                    default:
                        return null;
                }
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional
    public String deleteMember(Long id, User user){
        try {
            Member member = findMember(id);
            UserRoleEnum userRoleEnum = user.getRole();

            switch (userRoleEnum) {
                case USER -> {
                    if (StringUtils.equals(member.getUserName(), user.getUsername())) {
                        memberRepository.delete(member);
                        return "삭제 성공";
                    } else {
                        return "삭제 실패";
                    }
                }
                case ADMIN -> {
                    memberRepository.delete(member);
                    return "삭제 성공";
                }
                default -> {
                    return "삭제 실패";
                }
            }
        }
        catch (Exception ex) {
            throw ex;
        }
    }

}
