package softeer.carbook.domain.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.domain.user.dto.ModifyNickNameForm;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.NicknameNotExistException;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;
import softeer.carbook.domain.user.exception.NicknameDuplicateException;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

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

    // todo 로그아웃

    // 로그인한 사용자인지

    // 닉네임 변경 ( 자신 프로필 페이지 )
    @PatchMapping("/profile/modify/{nickname}")
    public ResponseEntity<?> modifyNickname(
            @PathVariable("nickname") String nickname,
            @Valid ModifyNickNameForm modifyNickNameForm,
            HttpServletRequest httpServletRequest
    ){
        Message resultMsg = userService.modifyNickname(
                nickname,
                modifyNickNameForm.getNewNickname(),
                httpServletRequest);
        return Message.make200Response(resultMsg.getMessage());
    }

    // exception handling

    // Valid 어노테이션 예외 처리
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Message> processValidationError(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        logger.warn(errorMsg);
        return Message.make400Response(errorMsg);
    }


    // 회원가입 시 이메일 중복 처리
    @ExceptionHandler(SignupEmailDuplicateException.class)
    public ResponseEntity<Message> signupEmailDuplicateException(SignupEmailDuplicateException emailDE){
        logger.debug(emailDE.getMessage());
        return Message.make400Response(emailDE.getMessage());
    }

    // 회원가입, 닉네임 변경 시 닉네임 중복 처리
    @ExceptionHandler(NicknameDuplicateException.class)
    public ResponseEntity<Message> nicknameDuplicateException(NicknameDuplicateException nicknameDE){
        logger.debug(nicknameDE.getMessage());
        return Message.make400Response(nicknameDE.getMessage());
    }

    // 로그인 시 등록된 이메일이 없는 경우 처리
    @ExceptionHandler(LoginEmailNotExistException.class)
    public ResponseEntity<Message> loginEmailNotExistException(LoginEmailNotExistException emailNE){
        logger.debug(emailNE.getMessage());
        return Message.make400Response(emailNE.getMessage());
    }

    // 닉네임이 데이터베이스에 존재하지 않는 경우 처리
    @ExceptionHandler(NicknameNotExistException.class)
    public ResponseEntity<Message> nicknameNotExistException(NicknameNotExistException nicknameNE){
        logger.debug(nicknameNE.getMessage());
        return Message.make400Response(nicknameNE.getMessage());
    }


}
