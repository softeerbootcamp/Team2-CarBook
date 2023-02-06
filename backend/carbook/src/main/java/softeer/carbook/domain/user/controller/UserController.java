package softeer.carbook.domain.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;
import softeer.carbook.domain.user.exception.SignupNicknameDuplicateException;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid SignupForm signupForm){
        Message resultMsg = userService.signup(signupForm);
        return Message.make200Response(resultMsg.getMessage());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Message> login(@Valid LoginForm loginForm, HttpSession session) {
        Message resultMsg = userService.login(loginForm, session);
        return Message.make200Response(resultMsg.getMessage());
    }

    // 로그아웃

    // 로그인한 사용자인지

    // exception handling

    // 회원가입 시 이메일 중복 처리
    @ExceptionHandler(SignupEmailDuplicateException.class)
    public ResponseEntity<Message> signupEmailDuplicateException(SignupEmailDuplicateException emailDE){
        logger.debug(emailDE.getMessage());
        return Message.make400Response("ERROR: Duplicated email");
    }

    // 회원가입 시 닉네임 중복 처리
    @ExceptionHandler(SignupNicknameDuplicateException.class)
    public ResponseEntity<Message> signupNicknameDuplicateException(SignupNicknameDuplicateException nicknameDE){
        logger.debug(nicknameDE.getMessage());
        return Message.make400Response("ERROR: Duplicated nickname");
    }

    // 로그인 시 등록된 이메일이 없는 경우 처리
    @ExceptionHandler(LoginEmailNotExistException.class)
    public ResponseEntity<Message> loginEmailNotExistException(LoginEmailNotExistException emailNE){
        logger.debug(emailNE.getMessage());
        return Message.make400Response("ERROR: Email not exist");
    }


}
