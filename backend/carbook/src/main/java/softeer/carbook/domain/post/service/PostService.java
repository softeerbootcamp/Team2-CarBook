package softeer.carbook.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.post.repository.S3Repository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.global.dto.Message;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final S3Repository s3Repository;
    private final int POST_COUNT = 10;

    @Autowired
    public PostService(
            PostRepository postRepository,
            ImageRepository imageRepository,
            UserRepository userRepository,
            FollowRepository followRepository,
            S3Repository s3Repository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.s3Repository = s3Repository;
    }

    public GuestPostsResponse getRecentPosts(int index) {
        List<Image> images = imageRepository.getImagesOfRecentPosts(POST_COUNT, index);
        return new GuestPostsResponse.GuestPostsResponseBuilder()
                .images(images)
                .build();
    }

    public LoginPostsResponse getRecentFollowerPosts(int index, User user){
        List<Image> images = imageRepository.getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId());
        return new LoginPostsResponse.LoginPostsResponseBuilder()
                .nickname(user.getNickname())
                .images(images)
                .build();
    }

    public PostsSearchResponse searchByTags(String hashtags, int index) {
        String[] tagNames = hashtags.split(" ");

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
        User profileUser = userRepository.findUserByNickname(profileUserNickname);
        return new OtherProfileResponse.OtherProfileResponseBuilder()
                .nickname(profileUserNickname)
                .email(profileUser.getEmail())
                .follow(followRepository.isFollow(loginUser.getId(), profileUser.getId()))
                .follower(followRepository.getFollowerCount(profileUser.getId()))
                .following(followRepository.getFollowingCount(profileUser.getId()))
                .images(imageRepository.findImagesByNickName(profileUserNickname))
                .build();
    }


    public Message createPost(NewPostForm newPostForm, User loginUser) {
        int model_id = 0;
        Post post = new Post(loginUser.getId(), newPostForm.getContent(), model_id);
        int postId = postRepository.addPost(post);
        String imageURL = "";
        try {
            imageURL = s3Repository.upload(newPostForm.getImage(), "images", postId);
        } catch (IllegalArgumentException iae){
            throw iae;
        }
        Image image = new Image(postId, imageURL);
        imageRepository.addImage(image);
        return new Message("Post create success");
    }

}
