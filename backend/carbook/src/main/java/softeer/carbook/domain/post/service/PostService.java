package softeer.carbook.domain.post.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.like.repository.LikeRepository;
import softeer.carbook.domain.post.dto.*;
import softeer.carbook.domain.post.exception.InvalidPostAccessException;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.post.repository.S3Repository;
import softeer.carbook.domain.tag.exception.HashtagNotExistException;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.model.Type;
import softeer.carbook.domain.tag.repository.TagRepository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.global.dto.Message;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final S3Repository s3Repository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final int POST_COUNT = 10;

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

    public LoginPostsResponse getRecentFollowerPosts(int index, User user) {
        List<Image> images = imageRepository.getImagesOfRecentFollowingPosts(POST_COUNT, index, user.getId());
        return new LoginPostsResponse.LoginPostsResponseBuilder()
                .nickname(user.getNickname())
                .images(images)
                .build();
    }

    public PostsSearchResponse searchByTags(String hashtags, String type, String model, int index) {
        List<Post> posts = new ArrayList<>();
        if (type != null) {
            posts.addAll(postRepository.searchByType(type));
        }
        logger.debug("size: {}", posts.size());

        if (model != null && isNeedToSearch(type, posts.size())) {
            findPostsOfModelTag(model, posts);
        }
        logger.debug("size: {}", posts.size());

        if (hashtags != null && isNeedToSearch(type, model, posts.size())) {
            findPostsOfHashTag(hashtags, posts);
        }
        logger.debug("size: {}", posts.size());

        List<Image> images = findImagesOfPostsStartsWithIndex(posts, index);
        return new PostsSearchResponse(images);
    }

    private void findPostsOfModelTag(String model, List<Post> posts) {
        if (posts.size() == 0) {
            posts.addAll(postRepository.searchByModel(model));
            return;
        }
        posts.retainAll(postRepository.searchByModel(model));
    }

    private void findPostsOfHashTag(String hashtags, List<Post> posts) {
        String[] tagNames = hashtags.split(" ");
        logger.debug("tagName: {}", tagNames[0]);

        if (posts.size() == 0) {
            posts.addAll(postRepository.searchByHashtag(tagNames[0]));
        } else {
            posts.retainAll(postRepository.searchByHashtag(tagNames[0]));
        }

        for (int idx = 1; idx < tagNames.length; idx++) {
            logger.debug("tagName: {}", tagNames[idx]);
            posts.retainAll(postRepository.searchByHashtag(tagNames[idx]));
        }
    }

    // 타입 태그가 있는데 검색 결과가 0인 경우, 해당하는 게시물이 없으니 모델 태그로 검색할 필요가 없다
    private boolean isNeedToSearch(String type, int size) {
        return !(type != null && size == 0);
    }

    // 타입 태그나 모델 태그가 있는데 검색 결과가 0인 경우, 해당하는 게시물이 없으니 해시태그로 검색할 필요가 없다
    private boolean isNeedToSearch(String type, String model, int size) {
        return !((type != null || model != null) && size == 0);
    }

    private List<Image> findImagesOfPostsStartsWithIndex(List<Post> posts, int index) {
        List<Image> images = new ArrayList<>();
        for (int cnt = index; cnt < index + POST_COUNT && cnt < posts.size(); cnt++) {
            Image image = imageRepository.getImageByPostId(posts.get(cnt).getId());
            images.add(image);
        }

        return images;
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
        } catch (IllegalArgumentException iae) {
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
        List<String> hashtags = tagRepository.findHashtagsByPostId(postId);
        List<Model> models = tagRepository.findModelByModelId(post.getModelId());
        List<Type> types = tagRepository.findTypeById(models.get(0).getTypeId());
        return new PostDetailResponse.PostDetailResponseBuilder()
                .isMyPost(isMyPost)
                .nickname(user.getNickname())
                .imageUrl(imageRepository.getImageByPostId(postId).getImageUrl())
                .like(likeRepository.findLikeCountByPostId(postId))
                .createDate(dateToString(post.getCreateDate()))
                .updateDate(dateToString(post.getUpdateDate()))
                .hashtags(hashtags)
                .model(models.get(0).getTag())
                .type(types.get(0).getTag())
                .content(post.getContent())
                .build();
    }

    private String dateToString(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    @Transactional
    public Message modifyPost(ModifiedPostForm modifiedPostForm, User user) {
        int postId = modifiedPostForm.getPostId();
        Post oldPost = postRepository.findPostById(postId);
        invalidPostAccessCheck(oldPost,user);
        Model model = tagRepository.findModelByName(modifiedPostForm.getModel());
        int modelId = model.getId();
        Post post = new Post(
                postId,
                new Timestamp(System.currentTimeMillis()),
                modifiedPostForm.getContent(),
                modelId
        );
        postRepository.updatePost(post);
        tagRepository.deletePostHashtags(postId);
        addPostHashtags(modifiedPostForm.getHashtag(), postId);
        Image oldImage = imageRepository.getImageByPostId(postId);
        s3Repository.deleteS3(getAWSFileName(oldImage));
        String imageURL = "";
        try {
            imageURL = s3Repository.upload(modifiedPostForm.getImage(), "images", postId);
        } catch (IllegalArgumentException iae) {
            throw iae;
        }
        Image newImage = new Image(postId, imageURL);
        imageRepository.updateImage(newImage);
        return new Message("Post modify success");
    }

    private String getAWSFileName(Image image) {
        String imageURL = image.getImageUrl();
        return imageURL.split("amazonaws\\.com/")[1];
    }

    private void addPostHashtags(List<String> tagNames, int postId) {
        for (String tagName : tagNames) {
            int tagId;
            try {
                tagId = tagRepository.findHashtagByName(tagName).getId();
            } catch (HashtagNotExistException hne) {
                tagId = tagRepository.addHashtag(new Hashtag(tagName));
            }
            tagRepository.addPostHashtag(postId, tagId);
        }
    }

    public Message deletePost(int postId, User user) {
        // 사용자가 작성한 글인지 확인
        Post post = postRepository.findPostById(postId);
        /*
        boolean isMyPost = (post.getUserId() == user.getId());
        // 본인이 작성한 글이 아닌데 삭제하려는 경우 예외처리
        if(!isMyPost) throw new InvalidPostAccessException();

         */
        invalidPostAccessCheck(post, user);
        // 게시글 삭제 진행
        postRepository.deletePostById(postId);

        return new Message("Post Deleted Successfully");
    }

    private void invalidPostAccessCheck(Post post, User user){
        if (!(post.getUserId() == user.getId()))
            throw new InvalidPostAccessException();
    }

}
