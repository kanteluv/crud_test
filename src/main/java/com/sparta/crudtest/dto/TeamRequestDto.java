package com.sparta.crudtest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamRequestDto {
    private String teamName;
    private String teamSport;
    private String teamSportContent;
}
