package com.sparta.crudtest.controller;

import com.sparta.crudtest.dto.MemberCommentRequestDto;
import com.sparta.crudtest.dto.MemberCommentResponseDto;
import com.sparta.crudtest.dto.MemberResponseDto;
import com.sparta.crudtest.security.UserDetailsImpl;
import com.sparta.crudtest.service.MemberCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class MemberCommentController {
    private final MemberCommentService memberCommentService;

    @PostMapping("/create/{memberId}")
    public ResponseEntity<MemberCommentResponseDto> createMemberComment(@PathVariable Long memberId, @RequestBody MemberCommentRequestDto memberCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MemberCommentResponseDto memberCommentResponseDto = memberCommentService.createMemberComment(memberId, memberCommentRequestDto, userDetails.getUser());
        if(memberCommentResponseDto != null){
            return ResponseEntity.ok()
                    .body(memberCommentResponseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<MemberCommentResponseDto> updateMemberComment(@PathVariable Long commentId, @RequestBody MemberCommentRequestDto memberCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       MemberCommentResponseDto memberCommentResponseDto = memberCommentService.updateMemberComment(commentId, memberCommentRequestDto, userDetails.getUser());
       if(memberCommentResponseDto != null) {
           return ResponseEntity.ok()
                   .body(memberCommentResponseDto);
       }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @DeleteMapping("/{commentId}")
    public String deleteMemberComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberCommentService.deleteMemberComment(commentId, userDetails.getUser());
    }
}
