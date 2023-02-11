package softeer.carbook.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(status().isOk());
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void modifyNickname() {
    }

    @Test
    void modifyPassword() {
    }
}