package com.sparta.crudtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.crudtest.dto.MemberCommentRequestDto;
import com.sparta.crudtest.dto.MemberCommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Getter
@NoArgsConstructor
public class MemberComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String commentName;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    @JsonIgnore
    private Member member;

    public MemberComment(MemberCommentRequestDto requestDto, Member member) {
        this.commentName = requestDto.getCommentName();
        this.commentContent = requestDto.getCommentContent();
        this.member = member;
    }

    public void update(MemberCommentRequestDto memberCommentRequestDto){
        this.commentName = memberCommentRequestDto.getCommentName();
        this.commentContent = memberCommentRequestDto.getCommentContent();
    }
}
