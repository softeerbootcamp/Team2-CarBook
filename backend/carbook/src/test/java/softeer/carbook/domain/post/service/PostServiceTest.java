package softeer.carbook.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.post.dto.GuestPostsResponse;
import softeer.carbook.domain.post.dto.LoginPostsResponse;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.user.controller.UserController;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.post.dto.PostsSearchResponse;
import softeer.carbook.domain.post.model.Image;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

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

    private final List<Image> user15FollowingImages = new ArrayList<>(List.of(
            new Image(8, "/eighth/image.jpg"),
            new Image(7, "/seventh/image.jpg"),
            new Image(6, "/sixth/image.jpg"),
            new Image(5, "/fifth/image.jpg")
    ));

    @Test
    @DisplayName("비로그인 상태 메인 페이지 테스트")
    void getRecentPostsTest() {
        //given
        int index = 0;
        //when
        GuestPostsResponse guestPostsResponse = postService.getRecentPosts(index);
        //then
        GuestPostsResponse expectedResult = new GuestPostsResponse.GuestPostsResponseBuilder()
                .images(images)
                .build();
        assertThat(guestPostsResponse).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("더 이상 불러올 게시글이 없을 때 테스트")
    void getNoPostsTest(){
        //given
        int index = 10;
        //when
        GuestPostsResponse guestPostsResponse = postService.getRecentPosts(index);
        //then
        GuestPostsResponse expectedResult = new GuestPostsResponse.GuestPostsResponseBuilder()
                .images(new ArrayList<Image>())
                .build();
        assertThat(guestPostsResponse).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("로그인 상태 메인 페이지 테스트")
    void getRecentFollowerPostsTest() {
        //given
        int index = 0;
        User user = new User(15,"user15@exam.com","15번유저","pw15");
        //when
        LoginPostsResponse loginPostsResponse = postService.getRecentFollowerPosts(index, user);
        //then
        LoginPostsResponse expectedResult = new LoginPostsResponse.LoginPostsResponseBuilder()
                .images(user15FollowingImages)
                .nickname(user.getNickname())
                .build();
        assertThat(loginPostsResponse).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("팔로잉중인 게시글이 없는 경우 테스트")
    void getNoFollowingPostsTest(){
        //given
        int index = 0;
        User user = new User(17,"user17@email.com","사용자17","pw17");
        //when
        LoginPostsResponse loginPostsResponse = postService.getRecentFollowerPosts(index, user);
        //then
        LoginPostsResponse expectedResult = new LoginPostsResponse.LoginPostsResponseBuilder()
                .images(new ArrayList<Image>())
                .nickname(user.getNickname())
                .build();
        assertThat(loginPostsResponse).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("해시태그를 통한 게시물 검색 기능 테스트")
    void searchByTags() {
        String hashtags = "맑음+흐림";

        PostsSearchResponse response = postService.searchByTags(hashtags, 0);
        List<Image> result = response.getImages();

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(0).getPostId()).isEqualTo(8);
        assertThat(result.get(1).getPostId()).isEqualTo(6);
        assertThat(result.get(2).getPostId()).isEqualTo(3);
        assertThat(result.get(3).getPostId()).isEqualTo(2);
        assertThat(result.get(0).getImageUrl()).isEqualTo("/eighth/image.jpg");
        assertThat(result.get(1).getImageUrl()).isEqualTo("/sixth/image.jpg");
        assertThat(result.get(2).getImageUrl()).isEqualTo("/third/image.jpg");
        assertThat(result.get(3).getImageUrl()).isEqualTo("/second/image.jpg");
    }

    @Test
    void myProfile() {
    }

}
