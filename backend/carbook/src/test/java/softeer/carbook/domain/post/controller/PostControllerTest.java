package softeer.carbook.domain.post.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.service.PostService;
import softeer.carbook.domain.user.exception.NotLoginStatementException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private PostService postService;

    private final List<Image> images = new ArrayList<>(List.of(
            new Image(8, "/eighth/image.jpg"),
            new Image(7, "/seventh/image.jpg"),
            new Image(6, "/sixth/image.jpg"),
            new Image(5, "/fifth/image.jpg"),
            new Image(4, "/fourth/image.jpg"),
            new Image(3, "/third/image.jpg"),
            new Image(2, "/second/image.jpg"),
            new Image(1, "/first/image.jpg")
    ));

    @Test
    @DisplayName("메인페이지 글 불러오기 테스트 - 로그인 상태일 경우")
    void getPostsIsLogin() throws Exception {
        // given
        LoginPostsResponse loginPostsResponse = new LoginPostsResponse.LoginPostsResponseBuilder()
                .nickname("nickname")
                .images(images)
                .build();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        given(userService.isLogin(httpServletRequest.getSession(false)))
                .willReturn(true);
        given(postService.getRecentFollowerPosts(anyInt(), any())).willReturn(loginPostsResponse);

        // when & then
        mockMvc.perform(get("/posts/m?index=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(true));
    }

    @Test
    @DisplayName("메인페이지 글 불러오기 테스트 - 로그인 상태가 아닐 경우")
    void getPostsIsNotLogin() throws Exception {
        // given
        GuestPostsResponse guestPostsResponse = new GuestPostsResponse.GuestPostsResponseBuilder()
                .images(images)
                .build();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        given(userService.isLogin(httpServletRequest.getSession(false)))
                .willReturn(false);
        given(postService.getRecentPosts(anyInt())).willReturn(guestPostsResponse);

        // when & then
        mockMvc.perform(get("/posts/m?index=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(false));
    }

    @Test
    @DisplayName("태그로 글 검색 테스트")
    void searchPostsByTags() throws Exception {
        // given
        PostsSearchResponse postsSearchResponse = new PostsSearchResponse(images);
        given(postService.searchByTags(anyString(), anyString(), anyString(), anyInt())).willReturn(postsSearchResponse);

        // when & then
        mockMvc.perform(get("/posts/m?index=0&hashtag=맑음+흐림+서울&type=세단&model=소나타"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("해당 사용자의 프로필 페이지 조회 - 로그인 확인 실패")
    void profileIsNotLogin() throws Exception {
        // given
        given(userService.findLoginedUser(any(HttpServletRequest.class))).willThrow(new NotLoginStatementException());

        // when & then
        mockMvc.perform(get("/profile?nickname=nickname"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("해당 사용자의 프로필 페이지 조회 - 내 프로필 페이지")
    void myProfile() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        MyProfileResponse myProfileResponse = new MyProfileResponse.MyProfileResponseBuilder()
                .build();
        OtherProfileResponse otherProfileResponse = new OtherProfileResponse.OtherProfileResponseBuilder()
                .build();
        given(userService.findLoginedUser(any(HttpServletRequest.class))).willReturn(user);
        given(postService.myProfile(any())).willReturn(myProfileResponse);
        given(postService.otherProfile(any(), anyString())).willReturn(otherProfileResponse);


        // when & then
        mockMvc.perform(get("/profile?nickname=nickname"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.myProfile").value(true));
    }

    @Test
    @DisplayName("해당 사용자의 프로필 페이지 조회 - 타인 프로필 페이지")
    void otherProfile() throws Exception {
        // given
        User user = new User("test@gmail.com", "nickname",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        MyProfileResponse myProfileResponse = new MyProfileResponse.MyProfileResponseBuilder()
                .build();
        OtherProfileResponse otherProfileResponse = new OtherProfileResponse.OtherProfileResponseBuilder()
                .build();
        given(userService.findLoginedUser(any(HttpServletRequest.class))).willReturn(user);
        given(postService.myProfile(any())).willReturn(myProfileResponse);
        given(postService.otherProfile(any(), anyString())).willReturn(otherProfileResponse);


        // when & then
        mockMvc.perform(get("/profile?nickname=nickname12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.myProfile").value(false));
    }


}