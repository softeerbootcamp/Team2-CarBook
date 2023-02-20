package softeer.carbook.domain.post.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql("classpath:create_table.sql")
@Sql("classpath:create_data.sql")
class ImageRepositoryTest {
    private static final int MODEL_COUNT = 52;
    private final Random rd = new Random();
    private UserRepository userRepository;
    private ImageRepository imageRepository;
    private PostRepository postRepository;
    private FollowRepository followRepository;
    @Autowired
    private DataSource dataSource;
    @BeforeEach
    void setUp(){
        userRepository = new UserRepository(dataSource);
        imageRepository = new ImageRepository(dataSource);
        postRepository = new PostRepository(dataSource);
    }

    @Test
    @DisplayName("PostId로 Image 조회하기 테스트")
    void getImageByPostIdTest() {
        Image image = new Image(1,"image/test.jpeg");
        userRepository.addUser(new User("test@gmail.com","john","bimil"));
        postRepository.addPost(new Post(1,"3번유저게시글",5));
        imageRepository.addImage(image);
        Image resultImage = imageRepository.getImageByPostId(image.getPostId());
        assertThat(resultImage).usingRecursiveComparison().isEqualTo(image);
    }

    @Test
    @DisplayName("[index,index+size) 범위의 게시글 가져오기 테스트")
    void getImagesOfRecentPostsTest() {
        int size = 10;
        int index = 5;
        List<Image> expectedImages = new ArrayList<>();
        for (int i=0; i<20; i++){
            int userId = rd.nextInt(100);
            int modelId = rd.nextInt(MODEL_COUNT-1)+1;
            Post post = new Post(userId,userId+"의 랜덤 글",modelId);
            Image image = new Image(postRepository.addPost(post), "image/"+i+".jpeg");
            imageRepository.addImage(image);
            if (i>=index && i<size+index)
                expectedImages.add(image);
        }
        List<Image> resultImages = imageRepository.getImagesOfRecentPosts(size, index);
        assertThat(resultImages).usingRecursiveComparison().isEqualTo(expectedImages);
    }

    @Test
    @DisplayName("[index,index+size) 범위의 팔로우 중인 게시글 가져오기 테스트")
    void getImagesOfRecentFollowingPostsTest() {
        int size = 8;
        int index = 2;
        int followerId = 100;

        //id 10, 20, 30인 유저를 팔로우 중
        List<Integer> followingIds = new ArrayList<>(){{
            add(10);
            add(20);
            add(30);
        }};

        //유저 팔로우
        for (int i: followingIds)
            followRepository.addFollow(followerId,i);

        addGuestPosts(5,9);

        //팔로잉 유저의 게시글 추가
        for (int userId: followingIds){
            for (int i = 1; i< 5; i++){
                int modelId = rd.nextInt(MODEL_COUNT-1)+1;
                Post post = new Post(userId,userId+"의 "+i+"번째 글",modelId);
                Image image = new Image(postRepository.addPost(post), "image/"+userId+"_"+i+".jpeg");
                imageRepository.addImage(image);
            }
        }

        addGuestPosts(5,9);

    }

    @Test
    void findImagesByUserId() {
    }

    @Test
    void findImagesByNickName() {
    }

    @Test
    @DisplayName("Image 저장하기 테스트")
    void addImage() {
        Image image = new Image(10,"image/test.jpeg");
        imageRepository.addImage(image);
        Image savedImage = imageRepository.getImageByPostId(10);
        assertThat(savedImage).usingRecursiveComparison().isEqualTo(image);
    }

    @Test
    void deleteImageByPostId() {
    }

    @Test
    void updateImage() {
    }

    private void addGuestPosts(int postCount, int userCount){
        for (int i=0; i<postCount; i++){
            int userId = rd.nextInt(userCount);
            int modelId = rd.nextInt(MODEL_COUNT-1)+1;
            Post post = new Post(userId,userId+"의 랜덤 글",modelId);
            Image image = new Image(postRepository.addPost(post), "image/"+i+".jpeg");
            imageRepository.addImage(image);
        }
    }
}
