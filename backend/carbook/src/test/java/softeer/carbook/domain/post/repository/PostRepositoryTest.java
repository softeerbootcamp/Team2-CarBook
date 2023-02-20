package softeer.carbook.domain.post.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.post.model.Post;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

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
        int userId = 1;
        String content = "content";
        int modelId = 1;
        Post post = new Post(1, content, 1);
        int postId = postRepository.addPost(post);

        Post result = postRepository.findPostById(postId);

        assertThat(result.getId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getModelId()).isEqualTo(modelId);
    }
}