package com.sparta.crudtest.service;

import com.sparta.crudtest.dto.MemberCommentRequestDto;
import com.sparta.crudtest.dto.MemberCommentResponseDto;
import com.sparta.crudtest.entity.*;
import com.sparta.crudtest.repository.MemberCommentRepository;
import com.sparta.crudtest.repository.MemberRepository;
import com.sparta.crudtest.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



@Service
@RequiredArgsConstructor
public class MemberCommentService {
    private final MemberCommentRepository memberCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberComment findMemberComment(Long id){
        return memberCommentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("코멘트가 존재하지 않습니다.")
        );
    }

    @Transactional
    public MemberCommentResponseDto createMemberComment(Long memberId, MemberCommentRequestDto memberCommentRequestDto, User user) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 선수 입니다.")
        );
        MemberComment memberComment = new MemberComment(memberCommentRequestDto, member);
        memberComment.setUsername(user.getUsername());
        memberCommentRepository.save(memberComment);
        MemberCommentResponseDto memberCommentResponseDto = new MemberCommentResponseDto(memberComment.getId(), memberComment.getUsername(), memberComment.getCommentName(), memberComment.getCommentContent());
        memberCommentResponseDto.setCreatedTime(memberComment.getCreatedAt());
        memberCommentResponseDto.setMember_id(memberId);
        return memberCommentResponseDto;
    }

    @Transactional
    public MemberCommentResponseDto updateMemberComment(Long id, MemberCommentRequestDto memberCommentRequestDto, User user) {
        try {
            MemberComment memberComment = findMemberComment(id);

            MemberCommentResponseDto memberCommentResponseDto;

            UserRoleEnum userRoleEnum = user.getRole();

            switch (userRoleEnum) {
                case USER -> {
                    if (StringUtils.pathEquals(memberComment.getUsername(), user.getUsername())) {
                        memberComment.update(memberCommentRequestDto);
                        memberCommentResponseDto = new MemberCommentResponseDto(memberComment.getId(), memberComment.getUsername(), memberComment.getCommentName(), memberComment.getCommentContent());
                        memberCommentResponseDto.setModifiedTime(memberComment.getModifiedAt());
                        memberCommentResponseDto.setMember_id(memberComment.getMember().getId());

                        return memberCommentResponseDto;
                    }
                }
                case ADMIN -> {
                    memberComment.update(memberCommentRequestDto);
                    memberCommentResponseDto = new MemberCommentResponseDto(memberComment.getId(), memberComment.getUsername(), memberComment.getCommentName(), memberComment.getCommentContent());
                    memberCommentResponseDto.setModifiedTime(memberComment.getModifiedAt());
                    memberCommentResponseDto.setMember_id(memberComment.getMember().getId());
                    return memberCommentResponseDto;
                }
            }
            return null;
        }catch(Exception ex){
            throw ex;
        }
    }

    @Transactional
    public String deleteMemberComment(Long id, User user){
        try {
            MemberComment memberComment = findMemberComment(id);

            UserRoleEnum userRoleEnum = user.getRole();

            switch (userRoleEnum) {
                case USER -> {
                    if (StringUtils.pathEquals(memberComment.getUsername(), user.getUsername())) {
                        memberCommentRepository.delete(memberComment);
                        return "삭제 성공";
                    }
                }
                case ADMIN -> {
                    memberCommentRepository.delete(memberComment);
                    return "삭제 성공";
                }
            }
            return "삭제 실패";
        } catch (Exception ex){
            throw ex;
        }
    }




}
