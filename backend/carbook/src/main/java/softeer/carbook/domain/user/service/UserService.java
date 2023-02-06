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
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;
import softeer.carbook.domain.user.exception.SignupNicknameDuplicateException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Message signup(SignupForm signupForm){
        // 중복 체크
        checkDuplicated(signupForm);
        // 데이터베이스에 유저 추가
        userRepository.addUser(signupForm);
        return new Message("SignUp Success");
    }

    private void checkDuplicated(SignupForm signupForm){
        if(userRepository.isEmailDuplicated(signupForm.getEmail()))
            throw new SignupEmailDuplicateException("중복된 이메일입니다.");
        if(userRepository.isNicknameDuplicated(signupForm.getNickname()))
            throw new SignupNicknameDuplicateException("중복된 닉네임입니다.");
    }

    public Message isLoginSuccess(LoginForm loginForm, HttpSession session) {
        User user = userRepository.findUserByEmail(loginForm.getEmail());
        if(Objects.equals(user.getPassword(), loginForm.getPassword())) {
            // 성공했을 경우 세션에 추가
            session.setAttribute("user", user);
            return new Message("Login Success");
        }
        return new Message("ERROR: Password not match");
    }

    public boolean isLogin(HttpServletRequest httpServletRequest){
        return httpServletRequest.getSession(false) != null;
    }

}
