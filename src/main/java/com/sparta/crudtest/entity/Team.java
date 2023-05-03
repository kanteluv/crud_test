package com.sparta.crudtest.entity;

import com.sparta.crudtest.dto.TeamRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String teamSport;

    @Column(nullable = false)
    private String teamSportContent;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Member> memberList = new ArrayList<>();

    public Team(TeamRequestDto teamRequestDto){
        this.teamName = teamRequestDto.getTeamName();
        this.teamSport = teamRequestDto.getTeamSport();
        this.teamSportContent = teamRequestDto.getTeamSportContent();
    }

    public void update(TeamRequestDto teamRequestDto){
        this.teamName = teamRequestDto.getTeamName();
        this.teamSport = teamRequestDto.getTeamSport();
        this.teamSportContent = teamRequestDto.getTeamSportContent();
    }
}
