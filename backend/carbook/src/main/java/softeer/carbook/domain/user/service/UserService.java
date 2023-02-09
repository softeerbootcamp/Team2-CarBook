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
import softeer.carbook.domain.user.exception.NotLoginStatementException;
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
        if(checkPassword(user, loginForm.getPassword())) {
            // 로그인 성공했을 경우 세션에 추가
            session.setAttribute("user", user.getId());
            // 세션에 추가 후 성공 메세지 반환
            return new Message("Login Success");
        }
        // 패스워드 불일치
        return new Message("ERROR: Password not match");
    }

    public Message logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        // 로그인 상태 확인
        checkLoginException(session);

        // 로그아웃 진행
        session.invalidate();

        return new Message("Logout Success");
    }

    public User findLoginedUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);

        // 로그인 상태 확인
        checkLoginException(session);

        // 세션으로부터 userId를 받아서 user 조회
        return userRepository.findUserById(getUserIdBySession(session));
    }

    public Message modifyNickname(String nickname, String newNickname, HttpServletRequest httpServletRequest) {
        // 로그인 체크
        checkLoginException(httpServletRequest.getSession(false));

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
        // 기존 비밀번호와 맞는지 확인
        // 세션을 통해 유저 불러오기 >> 함수 내에서 로그인 체크
        User modifyUser = findLoginedUser(httpServletRequest);
        if(!checkPassword(modifyUser, modifyPasswordForm.getPassword()))
            // 기존 비밀번호와 맞지 않을 경우 = 패스워드 불일치
            return new Message("ERROR: Password not match");

        // 새로운 비밀번호 반영
        userRepository.modifyPassword(modifyPasswordForm.getPassword(), modifyPasswordForm.getNewPassword());

        return new Message("Password modified successfully");
    }

    private int getUserIdBySession(HttpSession session){
        return (int) session.getAttribute("user");
    }

    public void checkLoginException(HttpSession session){
        if(!isLogin(session)) throw new NotLoginStatementException();
    }

    public boolean isLogin(HttpSession session){
        return session != null;
    }

    private boolean checkPassword(User user, String password){
        return user.getPassword().equals(password);
    }


}
