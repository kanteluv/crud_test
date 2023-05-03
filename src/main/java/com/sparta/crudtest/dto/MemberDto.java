package com.sparta.crudtest.dto;

import com.sparta.crudtest.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String userName;
    private String MemberName;
    private String MemberContent;
    private List<MemberCommentDto > memberCommentDtoList;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUserName();
        MemberName = member.getMemberName();
        MemberContent = member.getMemberContent();
    }
}
