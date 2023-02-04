package softeer.carbook.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm){
        userService.signup(signupForm);
        return "result";
    }

    // 로그인


    // 로그아웃

    // 로그인한 사용자인지

}
