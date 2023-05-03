package com.sparta.crudtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TeamResponseDto {
    private Long id;
    private String userName;
    private String teamName;
    private String teamSport;
    private String teamSportContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TeamResponseDto(Long id, String userName, String teamName, String teamSport, String teamSportContent) {
        this.id = id;
        this.userName = userName;
        this.teamName = teamName;
        this.teamSport = teamSport;
        this.teamSportContent = teamSportContent;
    }

    public void setCreatedTime(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setModifiedTime(LocalDateTime modifiedAt){
        this.modifiedAt = modifiedAt;
    }
}
