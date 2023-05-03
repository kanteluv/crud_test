package com.sparta.crudtest.controller;


import com.sparta.crudtest.dto.UserRequestDto;
import com.sparta.crudtest.service.UserService;
import com.sparta.crudtest.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@Valid @RequestBody UserRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for(FieldError fieldError: bindingResult.getFieldErrors()) {
             sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseDto(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserRequestDto requestDto, jakarta.servlet.http.HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
}