package softeer.carbook.domain.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.domain.user.dto.ModifyPasswordForm;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.NicknameNotExistException;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;
import softeer.carbook.domain.user.exception.NicknameDuplicateException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {
    private final UserRepository userRepository;
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
            throw new SignupEmailDuplicateException();
        if(userRepository.isNicknameDuplicated(signupForm.getNickname()))
            throw new NicknameDuplicateException();
    }

    public Message login(LoginForm loginForm, HttpSession session) {
        User user = userRepository.findUserByEmail(loginForm.getEmail());
        if(isLoginSuccess(user, loginForm)) {
            // 성공했을 경우 세션에 추가
            session.setAttribute("user", user);
            // 세션에 추가 후 성공 메세지 반환
            return new Message("Login Success");
        }
        // 패스워드 불일치
        return new Message("ERROR: Password not match");
    }

    private boolean isLoginSuccess(User user, LoginForm loginForm){
        return user.getPassword().equals(loginForm.getPassword());
    }

    public boolean isLogin(HttpServletRequest httpServletRequest){
        return httpServletRequest.getSession(false) != null;
    }

    public User findLoginedUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        return (User) session.getAttribute("user");
    }

    public Message modifyNickname(String nickname, String newNickname, HttpServletRequest httpServletRequest) {
        // 로그인 체크
        if(!isLogin(httpServletRequest))
            return new Message("ERROR: Session Has Expired");

        // 기존 닉네임이 데이터베이스에 없다??
        if(!userRepository.isNicknameDuplicated(nickname))
            throw new NicknameNotExistException();

        // 새로운 닉네임 중복 체크
        if(userRepository.isNicknameDuplicated(newNickname))
            throw new NicknameDuplicateException();

        // 새로운 닉네임 반영
        userRepository.modifyNickname(nickname, newNickname);

        return new Message("Nickname modified successfully");
    }

    public Message modifyPassword(ModifyPasswordForm modifyPasswordForm, HttpServletRequest httpServletRequest) {
        // 로그인 체크
        if(!isLogin(httpServletRequest))
            return new Message("ERROR: Session Has Expired");

        // 새로운 비밀번호 반영
        userRepository.modifyPassword(modifyPasswordForm.getPassword(), modifyPasswordForm.getNewPassword());

        return new Message("Password modified successfully");
    }
}
