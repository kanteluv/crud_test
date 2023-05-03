package com.sparta.crudtest.repository;

import com.sparta.crudtest.dto.TeamResponseDto;
import com.sparta.crudtest.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAll();

    @Query("select distinct t from Team t left join fetch Member m left join fetch MemberComment mc on m.id = mc.member.id")
    List<Team> selectAllInfo();
}
