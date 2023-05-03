package com.sparta.crudtest.service;

import com.sparta.crudtest.dto.UserRequestDto;
import com.sparta.crudtest.dto.ResponseDto;
import com.sparta.crudtest.dto.TokenDto;
import com.sparta.crudtest.entity.RefreshToken;
import com.sparta.crudtest.entity.User;
import com.sparta.crudtest.entity.UserRoleEnum;
import com.sparta.crudtest.repository.RefreshTokenRepository;
import com.sparta.crudtest.repository.UserRepository;
import com.sparta.crudtest.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.crudtest.util.JwtUtil.ACCESS_KEY;
import static com.sparta.crudtest.util.JwtUtil.REFRESH_KEY;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public ResponseDto signup(UserRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> found = userRepository.findById(requestDto.getUsername());

        if (found.isPresent()) {
            return new ResponseDto("아이디 중복", HttpStatus.BAD_REQUEST);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        

        return new ResponseDto("Success", HttpStatus.OK);
    }


    public ResponseDto login(UserRequestDto requestDto, jakarta.servlet.http.HttpServletResponse response) {

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        try {
            User user = userRepository.findById(requestDto.getUsername()).orElseThrow(
                    () -> new IllegalArgumentException("없는 ID 입니다.")
            );

            // 비밀번호 확인
            if(!passwordEncoder.matches(password, user.getPassword())){
                return new ResponseDto("비밀번호를 확인해주세요!!", HttpStatus.BAD_REQUEST);
            }

            //username (ID) 정보로 Token 생성
            TokenDto tokenDto = jwtUtil.createAllToken(requestDto.getUsername(), user.getRole());

            //Refresh 토큰 있는지 확인
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(requestDto.getUsername());

            //Refresh 토큰이 있다면 새로 발급 후 업데이트
            //없다면 새로 만들고 DB에 저장
            if (refreshToken.isPresent()) {
                RefreshToken savedRefreshToken = refreshToken.get();
                RefreshToken updateToken = savedRefreshToken.updateToken(tokenDto.getRefreshToken().substring(7));
                refreshTokenRepository.save(updateToken);
            } else {
                RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken().substring(7), username);
                refreshTokenRepository.save(newToken);
            }

            //응답 헤더에 토큰 추가
            setHeader(response, tokenDto);
            return new ResponseDto("성공", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void setHeader(jakarta.servlet.http.HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(ACCESS_KEY, tokenDto.getAccessToken());
        response.addHeader(REFRESH_KEY, tokenDto.getRefreshToken());
    }

}
