package softeer.carbook.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.like.repository.LikeRepository;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.exception.InvalidPostAccessException;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.tag.repository.TagRepository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.global.dto.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private TagRepository tagRepository;

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

    private final List<Post> posts = new ArrayList<>(List.of(
            new Post(8, 8, null, null, null, 8),
            new Post(6, 6, null, null, null, 6)
    ));
    private final Image image1 = new Image(8, "/eighth/image.jpg");
    private final Image image2 = new Image(6, "/sixth/image.jpg");
    private final List<Image> imagesEightAndSix = new ArrayList<Image>(List.of(
            new Image(8, "/eighth/image.jpg"),
            new Image(6, "/sixth/image.jpg")
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
    @DisplayName("해시태그, 타입, 모델 태그로 게시물을 검색한 경우 테스트")
    void searchByTagsWithHashtagsAndTypeAndModel() {
        // given
        int index = 0;
        String hashtags = "맑음 흐림";
        String type = "type";
        String model = "model";
        String[] tagNames = hashtags.split(" ");

        given(postRepository.searchByType(type)).willReturn(posts);
        given(postRepository.searchByModel(model)).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[0])).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[1])).willReturn(posts);
        given(imageRepository.getImageByPostId(8)).willReturn(image1);
        given(imageRepository.getImageByPostId(6)).willReturn(image2);

        // when
        PostsSearchResponse response = postService.searchByTags(hashtags, type, model, index);

        // then
        List<Image> result = response.getImages();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(imagesEightAndSix);
        verify(postRepository).searchByType(type);
        verify(postRepository).searchByModel(model);
        verify(postRepository).searchByHashtag(tagNames[0]);
        verify(postRepository).searchByHashtag(tagNames[1]);
        verify(imageRepository).getImageByPostId(8);
        verify(imageRepository).getImageByPostId(6);
    }

    @Test
    @DisplayName("해시태그, 모델 태그로 게시물을 검색한 경우 테스트")
    void searchByTagsWithHashtagsAndModel() {
        // given
        int index = 0;
        String hashtags = "맑음 흐림";
        String type = null;
        String model = "model";
        String[] tagNames = hashtags.split(" ");

        given(postRepository.searchByModel(model)).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[0])).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[1])).willReturn(posts);
        given(imageRepository.getImageByPostId(8)).willReturn(image1);
        given(imageRepository.getImageByPostId(6)).willReturn(image2);

        // when
        PostsSearchResponse response = postService.searchByTags(hashtags, type, model, index);

        // then
        List<Image> result = response.getImages();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(imagesEightAndSix);
        verify(postRepository).searchByModel(model);
        verify(postRepository).searchByHashtag(tagNames[0]);
        verify(postRepository).searchByHashtag(tagNames[1]);
        verify(imageRepository).getImageByPostId(8);
        verify(imageRepository).getImageByPostId(6);
    }

    @Test
    @DisplayName("해시태그, 타입 태그로 게시물을 검색한 경우 테스트")
    void searchByTagsWithHashtagsAndType() {
        // given
        int index = 0;
        String hashtags = "맑음 흐림";
        String type = "type";
        String model = null;
        String[] tagNames = hashtags.split(" ");

        given(postRepository.searchByType(type)).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[0])).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[1])).willReturn(posts);
        given(imageRepository.getImageByPostId(8)).willReturn(image1);
        given(imageRepository.getImageByPostId(6)).willReturn(image2);

        // when
        PostsSearchResponse response = postService.searchByTags(hashtags, type, model, index);

        // then
        List<Image> result = response.getImages();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(imagesEightAndSix);
        verify(postRepository).searchByType(type);
        verify(postRepository).searchByHashtag(tagNames[0]);
        verify(postRepository).searchByHashtag(tagNames[1]);
        verify(imageRepository).getImageByPostId(8);
        verify(imageRepository).getImageByPostId(6);
    }

    @Test
    @DisplayName("해시태그로만 게시물을 검색한 경우 테스트")
    void searchByTagsWithHashtags() {
        // given
        int index = 0;
        String hashtags = "맑음 흐림";
        String type = null;
        String model = null;
        String[] tagNames = hashtags.split(" ");

        given(postRepository.searchByHashtag(tagNames[0])).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[1])).willReturn(posts);
        given(imageRepository.getImageByPostId(8)).willReturn(image1);
        given(imageRepository.getImageByPostId(6)).willReturn(image2);

        // when
        PostsSearchResponse response = postService.searchByTags(hashtags, type, model, index);

        // then
        List<Image> result = response.getImages();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(imagesEightAndSix);
        verify(postRepository).searchByHashtag(tagNames[0]);
        verify(postRepository).searchByHashtag(tagNames[1]);
        verify(imageRepository).getImageByPostId(8);
        verify(imageRepository).getImageByPostId(6);
    }

    @Test
    @DisplayName("해시태그, 타입, 모델 태그로 게시물을 검색했는데 아무 결과도 얻지 못한 경우 테스트")
    void searchByTagsWithNoResult() {
        // given
        int index = 0;
        String hashtags = "맑음 흐림";
        String type = "type";
        String model = "model";
        String[] tagNames = hashtags.split(" ");

        given(postRepository.searchByType(type)).willReturn(posts);
        given(postRepository.searchByModel(model)).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[0])).willReturn(posts);
        given(postRepository.searchByHashtag(tagNames[1])).willReturn(new ArrayList<>());

        // when
        PostsSearchResponse response = postService.searchByTags(hashtags, type, model, index);

        // then
        List<Image> result = response.getImages();
        assertThat(result.size()).isEqualTo(0);
        assertThat(result).usingRecursiveComparison().isEqualTo(new ArrayList<>());
        verify(postRepository).searchByType(type);
        verify(postRepository).searchByModel(model);
        verify(postRepository).searchByHashtag(tagNames[0]);
        verify(postRepository).searchByHashtag(tagNames[1]);
    }

    @Test
    @DisplayName("나의 프로필 페이지 테스트")
    void myProfile() {
        // given
        String email = "user17@email.com";
        String nickname = "사용자17";
        User user = new User(17, email, nickname, "pw17");
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
        OtherProfileResponse expectedResult = new OtherProfileResponse.OtherProfileResponseBuilder()
                .nickname(profileUserNickname)
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

    @Test
    @DisplayName("내 글 상세 페이지 게시글 불러오기 테스트")
    void getMyPostDetails() {
        // given
        User user = new User(17, "user17@email.com", "사용자17", "pw17");
        Post post = new Post(1, 17, new Timestamp(12341241), new Timestamp(1231235), "asdf", 1);
        Image image = images.get(0);
        given(postRepository.findPostById(anyInt())).willReturn(post);
        given(imageRepository.getImageByPostId(anyInt())).willReturn(image);
        given(likeRepository.findLikeCountByPostId(anyInt())).willReturn(123);
        given(tagRepository.searchPostTagsByPostIdAndModelId(anyInt(), anyInt())).willReturn(new ArrayList<>());

        // when
        PostDetailResponse result = postService.getPostDetails(1, user);

        // then
        assertThat(result.isMyPost()).isTrue();
        verify(postRepository).findPostById(anyInt());
        verify(imageRepository).getImageByPostId(anyInt());
        verify(likeRepository).findLikeCountByPostId(anyInt());
        verify(tagRepository).searchPostTagsByPostIdAndModelId(anyInt(), anyInt());
    }

    @Test
    @DisplayName("타인의 글 상세 페이지 게시글 불러오기 테스트")
    void getOtherPostDetails() {
        // given
        User user = new User(17, "user17@email.com", "사용자17", "pw17");
        Post post = new Post(1, 1, new Timestamp(12341241), new Timestamp(1231235), "asdf", 1);
        Image image = images.get(0);
        given(postRepository.findPostById(anyInt())).willReturn(post);
        given(imageRepository.getImageByPostId(anyInt())).willReturn(image);
        given(likeRepository.findLikeCountByPostId(anyInt())).willReturn(123);
        given(tagRepository.searchPostTagsByPostIdAndModelId(anyInt(), anyInt())).willReturn(new ArrayList<>());

        // when
        PostDetailResponse result = postService.getPostDetails(1, user);

        // then
        assertThat(result.isMyPost()).isFalse();
        verify(postRepository).findPostById(anyInt());
        verify(imageRepository).getImageByPostId(anyInt());
        verify(likeRepository).findLikeCountByPostId(anyInt());
        verify(tagRepository).searchPostTagsByPostIdAndModelId(anyInt(), anyInt());
    }

    @Test
    @DisplayName("글 삭제 테스트 - 성공")
    void deletePost() {
        // given
        User user = new User(17, "user17@email.com", "사용자17", "pw17");
        Post post = new Post(1, 17, new Timestamp(12341241), new Timestamp(1231235), "asdf", 1);
        given(postRepository.findPostById(anyInt())).willReturn(post);

        // when
        Message result = postService.deletePost(1, user);

        // then
        assertThat(result.getMessage()).isEqualTo("Post Deleted Successfully");
        verify(postRepository).findPostById(anyInt());
    }

    @Test
    @DisplayName("글 삭제 테스트 - 실패: 남의 글 삭제")
    void deleteOtherUserPost() {
        // given
        User user = new User(17, "user17@email.com", "사용자17", "pw17");
        Post post = new Post(1, 1, new Timestamp(12341241), new Timestamp(1231235), "asdf", 1);
        given(postRepository.findPostById(anyInt())).willReturn(post);

        // when
        Throwable exception = assertThrows(InvalidPostAccessException.class, () -> {
            Message result = postService.deletePost(1, user);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("ERROR: Invalid Access");
        verify(postRepository).findPostById(anyInt());
    }


}
