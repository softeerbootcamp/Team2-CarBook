package softeer.carbook.domain.follow.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import softeer.carbook.domain.follow.dto.FollowListResponse;
import softeer.carbook.domain.follow.exception.FollowIdNotExistException;
import softeer.carbook.domain.follow.service.FollowService;
import softeer.carbook.domain.like.controller.LikeController;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.service.UserService;
import softeer.carbook.global.dto.Message;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
class FollowControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean private FollowService followService;
    @MockBean private UserService userService;

    @Test
    @DisplayName("팔로우 기능 테스트")
    void follow() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userService.findLoginedUser(any())).willReturn(user);
        given(followService.modifyFollowInfo(any(), anyString())).willReturn(new Message("Follow Success"));

        // when & then
        mockMvc.perform(post("/profile/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"followingNickname\":\"15번유저\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Follow Success")));
    }

    @Test
    @DisplayName("언팔로우 기능 테스트")
    void unFollow() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userService.findLoginedUser(any())).willReturn(user);
        given(followService.modifyFollowInfo(any(), anyString())).willReturn(new Message("Unfollow Success"));

        // when & then
        mockMvc.perform(post("/profile/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"followingNickname\":\"15번유저\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Unfollow Success")));
    }

    @Test
    @DisplayName("팔로우 리스트 응답 테스트")
    void getFollowers() throws Exception {
        // given
        FollowListResponse followListResponse = new FollowListResponse(new ArrayList<>());
        given(followService.getFollowers(anyString())).willReturn(followListResponse);

        // when & then
        mockMvc.perform(get("/profile/followers?nickname=test123"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팔로잉 리스트 응답 테스트")
    void getFollowings() throws Exception {
        // given
        FollowListResponse followListResponse = new FollowListResponse(new ArrayList<>());
        given(followService.getFollowings(anyString())).willReturn(followListResponse);

        // when & then
        mockMvc.perform(get("/profile/followings?nickname=test123"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팔로워 삭제 테스트 - 성")
    void deleteFollowerSuccess() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userService.findLoginedUser(any())).willReturn(user);
        given(followService.deleteFollower(any(), anyString())).willReturn(new Message("Follower delete success"));

        // when & then
        mockMvc.perform(delete("/profile/follower?follower=nickname"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("팔로워 삭제 테스트 - 실패")
    void deleteFollowerFail() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        given(userService.findLoginedUser(any())).willReturn(user);
        given(followService.deleteFollower(any(), anyString())).willThrow(new FollowIdNotExistException());

        // when & then
        mockMvc.perform(delete("/profile/follower?follower=nickname"))
                .andExpect(status().isBadRequest());
    }

}