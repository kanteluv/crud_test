package com.sparta.crudtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberCommentResponseDto {
    private Long id;
    private String userName;
    private String commentName;
    private String commentContent;
    private Long member_id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemberCommentResponseDto(Long id, String userName, String commentName, String commentContent){
        this.id = id;
        this.userName = userName;
        this.commentName = commentName;
        this.commentContent = commentContent;
    }

    public void setCreatedTime(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setModifiedTime(LocalDateTime modifiedAt){
        this.modifiedAt = modifiedAt;
    }

    public void setMember_id(Long member_id){
        this.member_id = member_id;
    }
}
