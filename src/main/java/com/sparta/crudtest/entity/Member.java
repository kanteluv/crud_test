package com.sparta.crudtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.crudtest.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String MemberName;

    @Column(nullable = false)
    private String MemberContent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    @JsonIgnore
    private Team team;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MemberComment> memberCommentList = new ArrayList<>();

    public Member(MemberRequestDto requestDto, Team team) {
        this.MemberName = requestDto.getMemberName();
        this.MemberContent = requestDto.getMemberContent();
        this.team = team;
    }

    public void update(MemberRequestDto requestDto){
        this.MemberName = requestDto.getMemberName();
        this.MemberContent = requestDto.getMemberContent();
    }
}
