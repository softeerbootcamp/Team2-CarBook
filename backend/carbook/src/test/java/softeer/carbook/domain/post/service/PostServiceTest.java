package softeer.carbook.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock private ImageRepository imageRepository;
    @Mock private UserRepository userRepository;
    @Mock private FollowRepository followRepository;

    private final int POST_COUNT = 10;
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
        given(imageRepository.getImagesOfRecentPosts(POST_COUNT, index)).willReturn(images);

        //when
        GuestPostsResponse guestPostsResponse = postService.getRecentPosts(index);

        //then
        assertThat(guestPostsResponse.isLogin()).isFalse();
        assertThat(guestPostsResponse.getImages()).isEqualTo(images);

        verify(imageRepository).getImagesOfRecentPosts(POST_COUNT, index);
    }

    @Test
    @DisplayName("더 이상 불러올 게시글이 없을 때 테스트")
    void getNoPostsTest() {
        //given
        int index = 10000;
        given(imageRepository.getImagesOfRecentPosts(POST_COUNT, index)).willReturn(new ArrayList<Image>());

        //when
        GuestPostsResponse guestPostsResponse = postService.getRecentPosts(index);

        //then
        assertThat(guestPostsResponse.isLogin()).isFalse();
        assertThat(guestPostsResponse.getImages()).isEqualTo(new ArrayList<Image>());

        verify(imageRepository).getImagesOfRecentPosts(POST_COUNT, index);
    }

    @Test
    @DisplayName("로그인 상태 메인 페이지 테스트")
    void getRecentFollowerPostsTest() {
        //given
        int index = 0;
        User user = new User(15, "user15@exam.com", "15번유저", "pw15");
        given(imageRepository.getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId())).willReturn(images);

        //when
        LoginPostsResponse loginPostsResponse = postService.getRecentFollowerPosts(index, user);

        //then
        assertThat(loginPostsResponse.isLogin()).isTrue();
        assertThat(loginPostsResponse.getImages()).isEqualTo(images);

        verify(imageRepository).getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId());
    }

    @Test
    @DisplayName("팔로잉중인 게시글이 없는 경우 테스트")
    void getNoFollowingPostsTest() {
        //given
        int index = 0;
        User user = new User(17, "user17@email.com", "사용자17", "pw17");
        given(imageRepository.getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId())).willReturn(new ArrayList<Image>());

        //when
        LoginPostsResponse loginPostsResponse = postService.getRecentFollowerPosts(index, user);

        //then
        assertThat(loginPostsResponse.isLogin()).isTrue();
        assertThat(loginPostsResponse.getImages()).isEqualTo(new ArrayList<Image>());

        verify(imageRepository).getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId());
    }

    @Test
    @DisplayName("해시태그를 통한 게시물 검색 기능 테스트")
    void searchByTags() {
        // given
        int index = 0;
        String hashtags = "맑음 흐림";
        String[] tagNames = hashtags.split(" ");
        List<Image> expectedResult = new ArrayList<Image>(List.of(
                new Image(8, "/eighth/image.jpg"),
                new Image(6, "/sixth/image.jpg"),
                new Image(3, "/third/image.jpg"),
                new Image(2, "/second/image.jpg")
        ));
        given(imageRepository.getImagesOfRecentPostsByTags(tagNames, POST_COUNT, index)).willReturn(expectedResult);

        // when
        PostsSearchResponse response = postService.searchByTags(hashtags, index);

        // then
        List<Image> result = response.getImages();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        verify(imageRepository).getImagesOfRecentPostsByTags(tagNames, POST_COUNT, index);
    }

    @Test
    @DisplayName("나의 프로필 페이지 테스트")
    void myProfile() {
        // given
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

        given(followRepository.getFollowerCount(user.getId())).willReturn(3);
        given(followRepository.getFollowingCount(user.getId())).willReturn(0);
        given(imageRepository.findImagesByUserId(user.getId())).willReturn(images);

        // when
        MyProfileResponse result = postService.myProfile(user);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        verify(followRepository).getFollowerCount(user.getId());
        verify(followRepository).getFollowingCount(user.getId());
        verify(imageRepository).findImagesByUserId(user.getId());
    }

    @Test
    @DisplayName("타인의 프로필 페이지 테스트")
    void otherProfile() {
        // given
        String email = "user15@exam.com";
        String nickname = "15번유저";
        User loginUser = new User(15, email, nickname, "pw15");
        User profileUser = new User(17, "user17@gmail.com", "사용자17", "pw17");
        String profileUserNickname = "사용자17";
        List<Image> images = new ArrayList<Image>(List.of(
                new Image(8, "/eighth/image.jpg"),
                new Image(7, "/seventh/image.jpg")
        ));
        OtherProfileResponse expectedResult = new OtherProfileResponse.OtherProfileResponseBuilder()
                .nickname(profileUser.getNickname())
                .email(profileUser.getEmail())
                .follow(true)
                .follower(3)
                .following(0)
                .images(images)
                .build();

        given(userRepository.findUserByNickname(profileUserNickname)).willReturn(profileUser);
        given(followRepository.isFollow(loginUser.getId(), profileUser.getId())).willReturn(true);
        given(followRepository.getFollowerCount(profileUser.getId())).willReturn(3);
        given(followRepository.getFollowingCount(profileUser.getId())).willReturn(0);
        given(imageRepository.findImagesByNickName(profileUserNickname)).willReturn(images);

        OtherProfileResponse result = postService.otherProfile(loginUser, profileUserNickname);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        verify(userRepository).findUserByNickname(profileUserNickname);
        verify(followRepository).isFollow(loginUser.getId(), profileUser.getId());
        verify(followRepository).getFollowerCount(profileUser.getId());
        verify(followRepository).getFollowingCount(profileUser.getId());
        verify(imageRepository).findImagesByNickName(profileUserNickname);
    }

}
