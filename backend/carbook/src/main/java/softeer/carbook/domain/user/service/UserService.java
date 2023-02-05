package softeer.carbook.domain.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;
import softeer.carbook.domain.user.exception.SignupNicknameDuplicateException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class UserService {
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<Message> signup(SignupForm signupForm){
        try {
            // 중복 체크
            checkDuplicated(signupForm);
        } catch (SignupEmailDuplicateException emailDE){
            // 이메일 중복 처리
            logger.debug(emailDE.getMessage());
            return new ResponseEntity<>(
                    new Message(emailDE.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (SignupNicknameDuplicateException nicknameDE){
            // 닉네임 중복 처리
            logger.debug(nicknameDE.getMessage());
            return new ResponseEntity<>(
                    new Message(nicknameDE.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }

        // 데이터베이스에 유저 추가
        userRepository.addUser(signupForm);
        return new ResponseEntity<>(
                new Message("SignUp Success"),
                HttpStatus.OK
        );
    }

    private void checkDuplicated(SignupForm signupForm){
        if(userRepository.isEmailDuplicated(signupForm.getEmail()))
            throw new SignupEmailDuplicateException("중복된 이메일입니다.");
        if(userRepository.isNicknameDuplicated(signupForm.getNickname()))
            throw new SignupNicknameDuplicateException("중복된 닉네임입니다.");
    }

    public boolean isLoginSuccess(LoginForm loginForm, HttpSession session) {
        User user = UserRepository.findUserByEmail(loginForm.getEmail());
        boolean isSuccess = Objects.equals(user.getPassword(), loginForm.getPassword());
        if (isSuccess) session.setAttribute("user", user);
        return isSuccess;
    }
}
