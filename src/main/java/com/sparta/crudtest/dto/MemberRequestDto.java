package com.sparta.crudtest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String memberName;
    private String memberContent;
}
