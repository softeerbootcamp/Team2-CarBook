package softeer.carbook.domain.user.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.service.UserService;

@Controller
public class UserController {
    // 회원가입
    @PostMapping("/signup")
    public String signup(SignupForm signupForm){
        UserService.signup(signupForm);
        return "result";
    }
    // 로그인


    // 로그아웃

    // 로그인한 사용자인지

}
