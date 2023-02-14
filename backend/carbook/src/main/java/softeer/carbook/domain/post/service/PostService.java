package softeer.carbook.domain.post.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.like.repository.LikeRepository;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.post.repository.S3Repository;
import softeer.carbook.domain.tag.exception.HashtagNotExistException;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.repository.TagRepository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.domain.user.service.UserService;
import softeer.carbook.global.dto.Message;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final S3Repository s3Repository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final int POST_COUNT = 10;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(
            PostRepository postRepository,
            ImageRepository imageRepository,
            UserRepository userRepository,
            FollowRepository followRepository,
            S3Repository s3Repository,
            TagRepository tagRepository,
            LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.s3Repository = s3Repository;
        this.tagRepository = tagRepository;
        this.likeRepository = likeRepository;
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

    public PostsSearchResponse searchByTags(String hashtags, String type, String model, int index) {
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

    @Transactional
    public Message createPost(NewPostForm newPostForm, User loginUser) {
        Model model = tagRepository.findModelByName(newPostForm.getModel());
        int modelId = model.getId();
        Post post = new Post(loginUser.getId(), newPostForm.getContent(), modelId);
        int postId = postRepository.addPost(post);
        addPostHashtags(newPostForm.getHashtag(), postId);
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

    public PostDetailResponse getPostDetails(int postId, User user) {
        // 내가 쓴 글인지 남이 쓴 글인지 판단
        Post post = postRepository.findPostById(postId);
        boolean isMyPost = (post.getUserId() == user.getId());
        return new PostDetailResponse.PostDetailResponseBuilder()
                .isMyPost(isMyPost)
                .nickname(user.getNickname())
                .imageUrl(imageRepository.getImageByPostId(postId).getImageUrl())
                .like(likeRepository.findLikeCountByPostId(postId))
                .createDate(dateToString(post.getCreateDate()))
                .updateDate(dateToString(post.getUpdateDate()))
                .keywords(tagRepository.searchPostTagsByPostIdAndModelId(postId, post.getModelId()))
                .content(post.getContent())
                .build();
    }

    private String dateToString(Timestamp date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    @Transactional
    public Message modifyPost(ModifiedPostForm modifiedPostForm) {
        Model model = tagRepository.findModelByName(modifiedPostForm.getModel());
        int modelId = model.getId();
        int postId = modifiedPostForm.getPostId();
        Post post = new Post(
                postId,
                new Timestamp(System.currentTimeMillis()),
                modifiedPostForm.getContent(),
                modelId
                );
        postRepository.updatePost(post);
        tagRepository.deletePostHashtags(postId);
        addPostHashtags(modifiedPostForm.getHashtag(),postId);
        Image oldImage = imageRepository.getImageByPostId(postId);
        s3Repository.deleteS3(getAWSFileName(oldImage));
        String imageURL = "";
        try {
            imageURL = s3Repository.upload(modifiedPostForm.getImage(), "images", postId);
        } catch (IllegalArgumentException iae){
            throw iae;
        }
        Image newImage = new Image(postId, imageURL);
        imageRepository.updateImage(newImage);
        return new Message("Post modify success");
    }

    private String getAWSFileName(Image image){
        String imageURL = image.getImageUrl();
        return imageURL.split("amazonaws\\.com/")[1];
    }

    private void addPostHashtags(List<String> tagNames, int postId){
        for (String tagName: tagNames){
            int tagId;
            try {
                tagId = tagRepository.findHashtagByName(tagName).getId();
            } catch (HashtagNotExistException hne){
                tagId = tagRepository.addHashtag(new Hashtag(tagName));
            }
            tagRepository.addPostHashtag(postId,tagId);
        }
    }
}
