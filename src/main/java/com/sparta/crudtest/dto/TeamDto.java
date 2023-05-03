package com.sparta.crudtest.dto;

import com.sparta.crudtest.entity.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TeamDto {
    private Long id;
    private String userName;
    private String teamName;
    private String teamSport;
    private String teamSportContent;
    private List<MemberDto> memberDtoList;

    public TeamDto(Team team){
        this.id = team.getId();
        this.userName = team.getUsername();
        this.teamName = team.getTeamName();
        this.teamSport = team.getTeamSport();
        this.teamSportContent = team.getTeamSportContent();
    }
}
