package com.sparta.crudtest.dto;

import com.sparta.crudtest.entity.MemberComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MemberCommentDto {
    private Long id;
    private String userName;
    private String commentName;
    private String commentContent;


    public MemberCommentDto(MemberComment memberComment) {
        this.id = memberComment.getId();
        this.userName = memberComment.getUsername();
        this.commentName = memberComment.getCommentName();
        this.commentContent = memberComment.getCommentContent();
    }
}
