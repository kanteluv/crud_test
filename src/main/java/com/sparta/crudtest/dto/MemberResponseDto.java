package com.sparta.crudtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String userName;
    private String memberName;
    private String memberContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long team_id;

    public MemberResponseDto(Long id, String userName, String memberName, String memberContent) {
        this.id = id;
        this.userName = userName;
        this.memberName = memberName;
        this.memberContent = memberContent;
    }

    public void setCreatedTime(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setModifiedTime(LocalDateTime modifiedAt){
        this.modifiedAt = modifiedAt;
    }

    public void setTeam_id(Long team_id){
        this.team_id = team_id;
    }
}
