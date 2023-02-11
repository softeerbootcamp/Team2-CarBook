package softeer.carbook.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.user.model.User;

import java.util.ArrayList;
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
    void getNoPostsTest() {
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
        User user = new User(15, "user15@exam.com", "15번유저", "pw15");
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
    void getNoFollowingPostsTest() {
        //given
        int index = 0;
        User user = new User(17, "user17@email.com", "사용자17", "pw17");
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
        String hashtags = "맑음 흐림";
        List<Image> expectedResult = new ArrayList<Image>(List.of(
                new Image(8, "/eighth/image.jpg"),
                new Image(6, "/sixth/image.jpg"),
                new Image(3, "/third/image.jpg"),
                new Image(2, "/second/image.jpg")
        ));

        PostsSearchResponse response = postService.searchByTags(hashtags, 0);
        List<Image> result = response.getImages();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("나의 프로필 페이지 테스트")
    void myProfile() {
        String email = "user17@email.com";
        String nickname = "사용자17";
        User user = new User(17, email, nickname, "pw17");
        List<Image> images = new ArrayList<Image>(List.of(
                new Image(8, "/eighth/image.jpg"),
                new Image(7, "/seventh/image.jpg")
        ));
        MyProfileResponse expectedResult = new MyProfileResponse.MyProfileResponseBuilder()
                .nickname(nickname)
                .email(email)
                .follower(3)
                .following(0)
                .images(images)
                .build();

        MyProfileResponse result = postService.myProfile(user);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("타인의 프로필 페이지 테스트")
    void otherProfile() {
        String email = "user15@exam.com";
        String nickname = "15번유저";
        User loginUser = new User(15, email, nickname, "pw15");
        String profileUserNickname = "사용자17";
        String profileUserEmail = "user17@email.com";
        List<Image> images = new ArrayList<Image>(List.of(
                new Image(8, "/eighth/image.jpg"),
                new Image(7, "/seventh/image.jpg")
        ));
        OtherProfileResponse expectedResult = new OtherProfileResponse.OtherProfileResponseBuilder()
                .nickname(profileUserNickname)
                .email(profileUserEmail)
                .follow(true)
                .follower(3)
                .following(0)
                .images(images)
                .build();

        OtherProfileResponse result = postService.otherProfile(loginUser, profileUserNickname);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}
