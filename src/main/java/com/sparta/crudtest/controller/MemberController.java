package com.sparta.crudtest.controller;

import com.sparta.crudtest.dto.MemberDto;
import com.sparta.crudtest.dto.MemberRequestDto;
import com.sparta.crudtest.dto.MemberResponseDto;
import com.sparta.crudtest.entity.Member;
import com.sparta.crudtest.entity.MemberComment;
import com.sparta.crudtest.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sparta.crudtest.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/select")
    public List<MemberDto> getMember() {
        return memberService.getMembers();
    }

//    @GetMapping("/select/{member_id}")
//    public Member getMember(@PathVariable Long member_id, @RequestBody MemberRequestDto memberRequestDto) {
//        Member member = memberService.getMembers(member_id, memberRequestDto);
//        return member;
//    }d

    @PostMapping("/create/{teamId}")
    public ResponseEntity<MemberResponseDto> createMember(@PathVariable Long teamId, @RequestBody MemberRequestDto memberRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MemberResponseDto memberResponseDto = memberService.createMember(teamId, memberRequestDto, userDetails.getUser());
        if(memberResponseDto != null){
            return ResponseEntity.ok()
                    .body(memberResponseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long memberId, @RequestBody MemberRequestDto memberRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MemberResponseDto memberResponseDto = memberService.updateMember(memberId, memberRequestDto, userDetails.getUser());
        if(memberResponseDto != null){
            return ResponseEntity.ok()
                    .body(memberResponseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @DeleteMapping("/{memberId}")
    public String deleteMember(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.deleteMember(memberId, userDetails.getUser());
    }
}
