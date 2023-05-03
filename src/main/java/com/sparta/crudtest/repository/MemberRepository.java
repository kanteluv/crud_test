package com.sparta.crudtest.repository;

import com.sparta.crudtest.dto.MemberDto;
import com.sparta.crudtest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
}
