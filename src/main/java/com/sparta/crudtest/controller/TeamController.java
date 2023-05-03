package com.sparta.crudtest.controller;

import com.sparta.crudtest.dto.TeamDto;
import com.sparta.crudtest.dto.TeamRequestDto;
import com.sparta.crudtest.dto.TeamResponseDto;
import com.sparta.crudtest.entity.Team;
import com.sparta.crudtest.security.UserDetailsImpl;
import com.sparta.crudtest.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<TeamResponseDto> createTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TeamResponseDto teamResponseDto = teamService.createTeam(teamRequestDto, userDetails.getUser());
        if (teamResponseDto != null) {
            return ResponseEntity.ok()
                    .body(teamResponseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> updateTeam(@PathVariable Long teamId, @RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TeamResponseDto teamResponseDto = teamService.updateTeam(teamId, teamRequestDto, userDetails.getUser());
        if (teamResponseDto != null) {
            return ResponseEntity.ok()
                    .body(teamResponseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @GetMapping("/select")
    public List<TeamDto> getTeams(){
        return teamService.getTeams();
    }

    @GetMapping("/select/all")
    public List<Team> getAllInfo(){
        return teamService.getInfo();
    }

    @DeleteMapping("/{teamId}")
    public String deleteTeam(@PathVariable Long teamId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return teamService.deleteTeam(teamId, userDetails.getUser());
    }
}

