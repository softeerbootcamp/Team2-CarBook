package softeer.carbook.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.global.dto.Message;
import softeer.carbook.domain.user.dto.ModifyPasswordForm;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    void signup() throws Exception {
        // given
        given(userService.signup(any(SignupForm.class)))
                .willReturn(new Message("SignUp Success"));

        // when & then
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"springtestemail@gmail.com\"," +
                                "\"nickname\":\"springtest\"," +
                                "\"password\":\"springtestpwd\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SignUp Success")));

    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        // given
        given(userService.login(
                any(LoginForm.class),
                any(HttpSession.class)))
                .willReturn(new Message("Login Success"));

        // when & then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"springtestemail@gmail.com\"," +
                                "\"password\":\"springtestpwd\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Login Success")));
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logout() throws Exception {
        // given
        given(userService.logout(any(HttpServletRequest.class)))
                .willReturn(new Message("Logout Success"));

        // when & then
        mockMvc.perform(post("/profile/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logout Success")));
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void modifyNickname() throws Exception {
        // given
        given(userService.modifyNickname(
                any(String.class),
                any(String.class),
                any(HttpServletRequest.class)))
                .willReturn(new Message("Nickname modified successfully"));

        // when & then
        mockMvc.perform(patch("/profile/modify/{nickname}", "springtest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newNickname\":\"newspringtest\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nickname modified successfully")));
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void modifyPassword() throws Exception {
        // given
        given(userService.modifyPassword(
                any(ModifyPasswordForm.class),
                any(HttpServletRequest.class)))
                .willReturn(new Message("Nickname modified successfully"));

        // when & then
        mockMvc.perform(patch("/profile/modify/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"springtestpwd\"," +
                                "\"newPassword\":\"springtestnewpwd\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nickname modified successfully")));
    }
}
