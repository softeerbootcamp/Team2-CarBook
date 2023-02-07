package softeer.carbook.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.hashtag.repository.HashtagRepository;
import softeer.carbook.domain.post.dto.GuestPostsResponse;
import softeer.carbook.domain.post.dto.LoginPostsResponse;
import softeer.carbook.domain.post.dto.PostsSearchResponse;
import softeer.carbook.domain.post.dto.MyProfileResponse;
import softeer.carbook.domain.post.dto.OtherProfileResponse;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final int POST_COUNT = 10;

    @Autowired
    public PostService(
            PostRepository postRepository,
            ImageRepository imageRepository,
            HashtagRepository hashtagRepository,
            UserRepository userRepository,
            FollowRepository followRepository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.hashtagRepository = hashtagRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public GuestPostsResponse getRecentPosts(int index) {
        List<Image> images = imageRepository.getImagesOfRecentPosts(POST_COUNT, index);
        return new GuestPostsResponse(false, images);
    }

    public LoginPostsResponse getRecentFollowerPosts(int index, User user){
        List<Image> images = imageRepository.getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId());
        return new LoginPostsResponse(true, user.getNickname(), images);
    }

    public PostsSearchResponse searchByTags(String hashtags, int index) {
        String[] tagNames = hashtags.split("\\+");

        List<Image> images = imageRepository.getImagesOfRecentPostsByTags(tagNames, POST_COUNT, index);
        return new PostsSearchResponse.PostsSearchResponseBuilder()
                .images(images)
                .build();
    }

    public MyProfileResponse myProfile(User loginUser) {
        return new MyProfileResponse.MyProfileResponseBuilder()
                .nickname(loginUser.getNickname())
                .email(loginUser.getEmail())
                .follower(followRepository.getFollowerCount(loginUser.getId()))
                .following(followRepository.getFollowingCount(loginUser.getId()))
                .images(imageRepository.findImagesByUserId(loginUser.getId()))
                .build();
    }

    public OtherProfileResponse otherProfile(User loginUser, String profileUserNickname) {
        int profileUserId = userRepository.findUserIdByNickname(profileUserNickname);
        return new OtherProfileResponse.OtherProfileResponseBuilder()
                .nickname(profileUserNickname)
                .email(userRepository.findEmailByNickname(profileUserNickname))
                .follow(followRepository.isFollow(loginUser.getId(), profileUserId))
                .follower(followRepository.getFollowerCount(profileUserId))
                .following(followRepository.getFollowingCount(profileUserId))
                .images(imageRepository.findImagesByNickName(profileUserNickname))
                .build();
    }
}
