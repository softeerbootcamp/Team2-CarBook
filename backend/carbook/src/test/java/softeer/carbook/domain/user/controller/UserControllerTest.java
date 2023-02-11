package softeer.carbook.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import softeer.carbook.domain.user.dto.LoginForm;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.ModifyNickNameForm;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
        SignupForm signupForm = new SignupForm(
                "springtestemail@gmail.com",
                "springtestpwd",
                "springtest"
        );
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
        LoginForm loginForm = new LoginForm(
                "springtestemail@gmail.com",
                "springtestpwd"
        );

        given(userService.login(any(LoginForm.class), any()))
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
    void logout() throws Exception{
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
        String nickname = "springtest";
        ModifyNickNameForm modifyNickNameForm = new ModifyNickNameForm("newspringtest");
        given(userService.modifyNickname(
                eq(nickname),
                eq(modifyNickNameForm.getNewNickname()),
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
    void modifyPassword() {
    }
}