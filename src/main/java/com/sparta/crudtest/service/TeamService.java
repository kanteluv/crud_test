package com.sparta.crudtest.service;

import com.sparta.crudtest.dto.MemberDto;
import com.sparta.crudtest.dto.TeamDto;
import com.sparta.crudtest.dto.TeamRequestDto;
import com.sparta.crudtest.dto.TeamResponseDto;
import com.sparta.crudtest.entity.Member;
import com.sparta.crudtest.entity.Team;
import com.sparta.crudtest.entity.User;
import com.sparta.crudtest.entity.UserRoleEnum;
import com.sparta.crudtest.repository.MemberRepository;
import com.sparta.crudtest.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Team findTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 팀 입니다")
        );
    }

    @Transactional
    public List<MemberDto> getAllMember(){
        List<Member> memberList;
        memberList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for(Member member : memberList){
            MemberDto memberDto = new MemberDto(member);
            memberDtoList.add(memberDto);
        }

        return memberDtoList;
    }

    @Transactional(readOnly = true)
    public List<TeamDto> getTeams() {
        List<Team> teamList = teamRepository.findAll();
        List<MemberDto> memberDtoList = getAllMember();
        List<TeamDto> teamDtoList = new ArrayList<>();

        for(Team team : teamList){
            TeamDto teamDto = new TeamDto(team);

            List<MemberDto> memberAtTeamIdDto = memberDtoList.stream()
                    .filter(a -> Objects.equals(a.getId(), teamDto.getId()))
                    .collect(Collectors.toList());

            teamDto.setMemberDtoList(memberAtTeamIdDto);
            teamDtoList.add(teamDto);
        }
        return teamDtoList;
    }

    @Transactional(readOnly = true)
    public List<Team> getInfo(){
        List<Team> teamList = teamRepository.selectAllInfo();
        return teamList;
    }

    @Transactional
    public TeamResponseDto createTeam(TeamRequestDto teamRequestDto, User user) {
        Team team = new Team(teamRequestDto);
        team.setUsername(user.getUsername());
        teamRepository.save(team);
        TeamResponseDto teamResponseDto = new TeamResponseDto(team.getId(), team.getUsername(), team.getTeamName(), team.getTeamSport(), team.getTeamSportContent());
        teamResponseDto.setCreatedTime(team.getCreatedAt());
        return teamResponseDto;
    }

    @Transactional
    public TeamResponseDto updateTeam(Long id, TeamRequestDto teamRequestDto, User user) {
        try {
            Team team = findTeam(id);
            UserRoleEnum userRoleEnum = user.getRole();

                switch (userRoleEnum) {
                    case USER:
                        if (StringUtils.equals(team.getUsername(), user.getUsername())) {
                            team.update(teamRequestDto);
                            TeamResponseDto teamResponseDto = new TeamResponseDto(team.getId(), team.getUsername(), team.getTeamName(), team.getTeamSport(), team.getTeamSportContent());
                            teamResponseDto.setModifiedTime(team.getModifiedAt());
                            return teamResponseDto;
                        }
                    case ADMIN:
                        team.update(teamRequestDto);
                        TeamResponseDto teamResponseDto = new TeamResponseDto(team.getId(), team.getUsername(), team.getTeamName(), team.getTeamSport(), team.getTeamSportContent());
                        teamResponseDto.setModifiedTime(team.getModifiedAt());
                        return teamResponseDto;
                    default:
                        return null;
                }
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public String deleteTeam(Long id, User user){
        try {
            Team team = findTeam(id);
            UserRoleEnum userRoleEnum = user.getRole();

            switch (userRoleEnum) {
                case USER -> {
                    if (StringUtils.equals(team.getUsername(), user.getUsername())) {
                        teamRepository.delete(team);
                        return "삭제 성공";
                    } else {
                        return "삭제 실패";
                    }
                }
                case ADMIN -> {
                    teamRepository.delete(team);
                    return "삭제 성공";
                }
                default -> {
                    return "삭제 실패";
                }
            }
        }
        catch (Exception ex) {
            throw ex;
        }
    }

}
