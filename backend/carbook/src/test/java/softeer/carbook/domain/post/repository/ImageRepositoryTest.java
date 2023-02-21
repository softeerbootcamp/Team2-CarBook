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
    private ImageRepository imageRepository;
    private PostRepository postRepository;
    @Autowired
    private DataSource dataSource;
    @BeforeEach
    void setUp(){
        imageRepository = new ImageRepository(dataSource);
        postRepository = new PostRepository(dataSource);
    }

    @Test
    @DisplayName("PostId로 Image 조회하기 테스트")
    void getImageByPostIdTest() {
        Image expectedImage = new Image(1, "https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/1_이미지.jpeg");
        Image resultImage = imageRepository.getImageByPostId(1);
        assertThat(resultImage).usingRecursiveComparison().isEqualTo(expectedImage);
    }

    @Test
    @DisplayName("[postId,postId+size) 범위의 게시글 가져오기 테스트")
    void getImagesOfRecentPostsTest() {
        int size = 3;
        int postId = 8;
        List<Image> expectedImages = new ArrayList<>();
        expectedImages.add(new Image(7, "https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/7_이미지.jpeg"));
        expectedImages.add(new Image(5, "https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/5_이미지.jpeg"));
        expectedImages.add(new Image(4, "https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/4_이미지.jpeg"));
        List<Image> resultImages = imageRepository.getImagesOfRecentPosts(size, postId);
        assertThat(resultImages).usingRecursiveComparison().isEqualTo(expectedImages);
    }

    @Test
    @DisplayName("[index,index+size) 범위의 팔로우 중인 게시글 가져오기 테스트")
    void getImagesOfRecentFollowingPostsTest() {
        int size = 2;
        int index = 6;
        int followerId = 1;
        List<Image> expectedImages = new ArrayList<>();
        expectedImages.add(new Image(5,"https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/5_이미지.jpeg"));
        expectedImages.add(new Image(4,"https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/4_이미지.jpeg"));
        List<Image> resultImages = imageRepository.getImagesOfRecentFollowingPosts(size, index, followerId);
        assertThat(resultImages).usingRecursiveComparison().isEqualTo(expectedImages);
    }

    @Test
    @DisplayName("User Id로 이미지 조회 테스트")
    void findImagesByUserId() {
        int userId = 1;
        List<Image> resultImages = imageRepository.findImagesByUserId(userId);
        List<Image> expectedImages = new ArrayList<>();
        expectedImages.add(new Image(1,"https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/1_이미지.jpeg"));
        expectedImages.add(new Image(2,"https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/2_이미지.jpeg"));
        assertThat(resultImages).usingRecursiveComparison().isEqualTo(expectedImages);
    }

    @Test
    @DisplayName("닉네임으로 이미지 조회 테스트")
    void findImagesByNickNameTest() {
        String nickname = "testname1";
        List<Image> resultImages = imageRepository.findImagesByNickName(nickname);
        List<Image> expectedImages = new ArrayList<>();
        expectedImages.add(new Image(1,"https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/1_이미지.jpeg"));
        expectedImages.add(new Image(2,"https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/2_이미지.jpeg"));
        assertThat(resultImages).usingRecursiveComparison().isEqualTo(expectedImages);
    }

    @Test
    @DisplayName("Image 저장하기 테스트")
    void addImage() {
        int userId = 1;
        int postId = postRepository.addPost(new Post(userId,userId+"의 새 글",10));
        Image expectedImage = new Image(postId, "https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/새로운_이미지.jpeg");
        imageRepository.addImage(expectedImage);
        Image resultImage = imageRepository.getImageByPostId(postId);
        assertThat(resultImage).usingRecursiveComparison().isEqualTo(expectedImage);
    }

    @Test
    @DisplayName("Image 수정하기 테스트")
    void updateImage() {
        int postId = 1;
        Image expectedImage = new Image(postId, "https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/수정_이미지.jpeg");
        imageRepository.updateImage(expectedImage);
        Image resultImage = imageRepository.getImageByPostId(postId);
        assertThat(resultImage).usingRecursiveComparison().isEqualTo(expectedImage);
    }
}
