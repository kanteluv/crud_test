package com.sparta.crudtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCommentRequestDto {
    private String commentName;
    private String commentContent;
}
