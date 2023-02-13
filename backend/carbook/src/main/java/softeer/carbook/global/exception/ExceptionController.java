package softeer.carbook.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import softeer.carbook.global.dto.Message;
import softeer.carbook.domain.user.exception.*;

@RestControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // Valid 어노테이션 예외 처리
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Message> processValidationError(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        logger.debug(errorMsg);
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

    // 로그인 시 패스워드 불일치 시 처리
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Message> passwordNotMatchException(PasswordNotMatchException passwordNME){
        logger.debug(passwordNME.getMessage());
        return Message.make400Response(passwordNME.getMessage());
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

    // 로그인 상태가 아닌 경우 처리
    @ExceptionHandler(NotLoginStatementException.class)
    public ResponseEntity<Message> notLoginStatementException(NotLoginStatementException notLoginE){
        logger.debug(notLoginE.getMessage());
        return Message.make400Response(notLoginE.getMessage());
    }
}
