package softeer.carbook.domain.like.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import softeer.carbook.domain.like.service.LikeService;
import softeer.carbook.domain.post.service.PostService;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.service.UserService;
import softeer.carbook.global.dto.Message;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private LikeService likeService;

    @Test
    @DisplayName("좋아요 테스트")
    void like() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userService.findLoginedUser(any())).willReturn(user);
        given(likeService.modifyLikeInfo(anyInt(), anyInt())).willReturn(new Message("Like Success"));

        // when & then
        mockMvc.perform(post("/post/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\": 10}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Like Success")));
    }

    @Test
    @DisplayName("좋아요 취소 테스트")
    void unLike() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userService.findLoginedUser(any())).willReturn(user);
        given(likeService.modifyLikeInfo(anyInt(), anyInt())).willReturn(new Message("UnLike Success"));

        // when & then
        mockMvc.perform(post("/post/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\": 10}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("UnLike Success")));
    }
}
