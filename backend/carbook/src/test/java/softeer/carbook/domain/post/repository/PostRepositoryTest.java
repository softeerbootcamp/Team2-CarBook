package softeer.carbook.domain.post.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.post.exception.PostNotExistException;
import softeer.carbook.domain.post.model.Post;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Sql("classpath:create_table.sql")
@Sql("classpath:create_data.sql")
class PostRepositoryTest {

    private PostRepository postRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        postRepository = new PostRepository(dataSource);
    }

    @Test
    @DisplayName("게시글 추가 테스트")
    void addPost() {
        // given
        int userId = 1;
        String content = "content";
        int modelId = 1;
        Post post = new Post(1, content, 1);

        // when
        int postId = postRepository.addPost(post);

        // then
        Post result = postRepository.findPostById(postId);
        assertThat(result.getId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getModelId()).isEqualTo(modelId);
    }

    @Test

    @DisplayName("인기글 조회 테스트")
    void getImagesOfPopularPostsDuringWeek() {
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        String lastWeekDay = lastWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Post> result = postRepository.findPopularPostsDuringWeek(lastWeekDay);

        assertThat(result.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("차량 종류 이름으로 게시글 검색 테스트")
    void searchByType() {
        // given
        String type = "수소 / 전기차";

        // when
        List<Post> result = postRepository.searchByType(type);

        // then
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("차량 모델 이름으로 게시글 검색 테스트")
    void searchByModel() {
        // given
        String model = "아이오닉 6";

        // when
        List<Post> result = postRepository.searchByModel(model);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("해시태그 이름으로 게시글 검색 테스트")
    void searchByHashtag() {
        // given
        String hashtag = "테스트태그1";

        // when
        List<Post> result = postRepository.searchByHashtag(hashtag);

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePost() {
        // given
        int postId = 1;
        String content = "안녕하세요";
        Timestamp updateDate = new Timestamp(System.currentTimeMillis());
        int modelId = 2;
        Post post = new Post(postId, updateDate, content, modelId);

        // when
        postRepository.updatePost(post);

        // then
        Post result = postRepository.findPostById(postId);
        assertThat(result.getId()).isEqualTo(postId);
        assertThat(result.getUpdateDate()).isEqualTo(updateDate);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getModelId()).isEqualTo(modelId);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePostById() {
        // given
        int postId = 1;

        // when
        postRepository.deletePostById(postId);

        // then
        Throwable exception = assertThrows(PostNotExistException.class, () -> postRepository.findPostById(postId));
        assertThat(exception.getMessage()).isEqualTo("ERROR: Post not exist");
    }
}