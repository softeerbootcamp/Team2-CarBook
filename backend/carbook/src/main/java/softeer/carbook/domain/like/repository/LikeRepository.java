package softeer.carbook.domain.like.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Objects;

@Repository
public class LikeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public int findLikeCountByPostId(int postId) {
        return Objects.requireNonNull(jdbcTemplate.queryForObject(
                "select count(id) from POST_LIKE where post_id = ?",
                Integer.class,
                postId));
    }
}
