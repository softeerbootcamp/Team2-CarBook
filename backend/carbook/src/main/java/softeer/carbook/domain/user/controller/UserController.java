package softeer.carbook.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid SignupForm signupForm){
        return userService.signup(signupForm);
    }

    // 로그인


    // 로그아웃

    // 로그인한 사용자인지

}
