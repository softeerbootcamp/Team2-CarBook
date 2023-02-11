package softeer.carbook.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.NicknameDuplicateException;
import softeer.carbook.domain.user.exception.PasswordNotMatchException;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup_success() {
        // given
        SignupForm signupForm = new SignupForm("test123@gmail.com", "testNickname", "testtest123");
        // when
        Message resultMsg = userService.signup(signupForm);
        // then
        assertThat(resultMsg.getMessage()).isEqualTo("SignUp Success");
    }

    @Test
    @DisplayName("회원가입 중복된 이메일 입력될 경우")
    void signupEmailDuplicate() {
        // given
        SignupForm signupForm = new SignupForm("test@gmail.com", "testNickname", "testtest123");
        String exceptionMsg = "";
        // when
        try {
            Message resultMsg = userService.signup(signupForm);
        } catch (SignupEmailDuplicateException e) {
            exceptionMsg = e.getMessage();
        }
        // then
        assertThat(exceptionMsg).isEqualTo("ERROR: Duplicated email");
    }

    @Test
    @DisplayName("회원가입 중복된 닉네임 입력될 경우")
    void signupNicknameDuplicate() {
        // given
        SignupForm signupForm = new SignupForm("test123@gmail.com", "carbook123", "15번유저");
        String exceptionMsg = "";
        // when
        try {
            Message resultMsg = userService.signup(signupForm);
        } catch (NicknameDuplicateException e) {
            exceptionMsg = e.getMessage();
        }
        // then
        assertThat(exceptionMsg).isEqualTo("ERROR: Duplicated nickname");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // given
        LoginForm loginForm = new LoginForm("test@gmail.com", "carbook");
        // when
        Message resultMsg = userService.login(loginForm, new MockHttpSession());
        // then
        assertThat(resultMsg.getMessage()).isEqualTo("Login Success");
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 이메일 없을 경우")
    void loginEmailNotExist() {
        // given
        LoginForm loginForm = new LoginForm("test123@gmail.com", "카북화이팅");
        String exceptionMsg = "";
        // when
        try {
            Message resultMsg = userService.login(loginForm, new MockHttpSession());
        } catch (LoginEmailNotExistException e) {
            exceptionMsg = e.getMessage();
        }
        // then
        assertThat(exceptionMsg).isEqualTo("ERROR: Email not exist");
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호 불일치")
    void loginPasswordNotMatch() {
        // given
        LoginForm loginForm = new LoginForm("test@gmail.com", "카북화이팅123");
        String exceptionMsg = "";
        // when
        try{
            Message resultMsg = userService.login(loginForm, new MockHttpSession());
        } catch (PasswordNotMatchException e) {
            exceptionMsg = e.getMessage();
        }
        // then
        assertThat(exceptionMsg).isEqualTo("ERROR: Password not match");
    }

}