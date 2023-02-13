package softeer.carbook.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.global.dto.Message;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.NicknameDuplicateException;
import softeer.carbook.domain.user.exception.PasswordNotMatchException;
import softeer.carbook.domain.user.exception.SignupEmailDuplicateException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup_Success() {
        // Given
        SignupForm signupForm = new SignupForm("email@example.com", "password", "nickname");
        given(userRepository.isEmailDuplicated(any())).willReturn(false);
        given(userRepository.isNicknameDuplicated(any())).willReturn(false);

        // When
        Message result = userService.signup(signupForm);

        // Then
        assertThat(result.getMessage()).isEqualTo("SignUp Success");
        verify(userRepository).isEmailDuplicated(signupForm.getEmail());
        verify(userRepository).isNicknameDuplicated(signupForm.getNickname());
    }

    @Test
    @DisplayName("회원가입 중복된 이메일 입력될 경우")
    void signupEmailDuplicate() {
        // Given
        SignupForm signupForm = new SignupForm("email@example.com", "password", "nickname");
        given(userRepository.isEmailDuplicated(any())).willReturn(true);

        // When
        Throwable exception = assertThrows(SignupEmailDuplicateException.class, () -> {
            Message resultMsg = userService.signup(signupForm);
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("ERROR: Duplicated email");
        verify(userRepository).isEmailDuplicated(signupForm.getEmail());
    }

    @Test
    @DisplayName("회원가입 중복된 닉네임 입력될 경우")
    void signupNicknameDuplicate() {
        // Given
        SignupForm signupForm = new SignupForm("email@example.com", "password", "nickname");
        given(userRepository.isEmailDuplicated(any())).willReturn(false);
        given(userRepository.isNicknameDuplicated(any())).willReturn(true);

        // When
        Throwable exception = assertThrows(NicknameDuplicateException.class, () -> {
            Message resultMsg = userService.signup(signupForm);
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("ERROR: Duplicated nickname");
        verify(userRepository).isEmailDuplicated(signupForm.getEmail());
        verify(userRepository).isNicknameDuplicated(signupForm.getNickname());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // Given
        LoginForm loginForm = new LoginForm("test@gmail.com",
                "password");
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userRepository.findUserByEmail(any())).willReturn(user);

        // When
        Message resultMsg = userService.login(loginForm, new MockHttpSession());

        // Then
        assertThat(resultMsg.getMessage()).isEqualTo("Login Success");
        verify(userRepository).findUserByEmail(loginForm.getEmail());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 이메일 없을 경우")
    void loginEmailNotExist() {
        // Given
        LoginForm loginForm = new LoginForm("test@gmail.com", "password");
        given(userRepository.findUserByEmail(any())).willThrow(new LoginEmailNotExistException());

        // When
        Throwable exception = assertThrows(LoginEmailNotExistException.class, () -> {
            Message resultMsg = userService.login(loginForm, new MockHttpSession());
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("ERROR: Email not exist");
        verify(userRepository).findUserByEmail(loginForm.getEmail());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 비밀번호 불일치")
    void loginPasswordNotMatch() {
        // Given
        LoginForm loginForm = new LoginForm("test@gmail.com", "password");
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password123", BCrypt.gensalt()));
        given(userRepository.findUserByEmail(any())).willReturn(user);

        // When
        Throwable exception = assertThrows(PasswordNotMatchException.class, () -> {
            Message resultMsg = userService.login(loginForm, new MockHttpSession());
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("ERROR: Password not match");
        verify(userRepository).findUserByEmail(loginForm.getEmail());
    }

    @Test
    @DisplayName("닉네임 변경 테스트 - 성공")
    void modifyNicknameSuccess(){
        // Given
        String nickname = "nickname";
        String newNickname = "newnickname";
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        given()
        given(userRepository.isNicknameDuplicated(nickname)).willReturn(true);
        given(userRepository.isNicknameDuplicated(newNickname)).willReturn(false);

        // When
        Message resultMsg = userService.modifyNickname(nickname, newNickname, httpServletRequest);

        // Then
        assertThat(resultMsg.getMessage()).isEqualTo("Nickname modified successfully");
        verify(httpServletRequest).getSession(false);
        verify(userRepository).isNicknameDuplicated(nickname);
        verify(userRepository).isNicknameDuplicated(newNickname);
        verify(userRepository).modifyNickname(nickname, newNickname);
    }

}
