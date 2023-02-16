package softeer.carbook.domain.like.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.user.model.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public boolean checkLike(int userId, int postId) {
        List<Integer> result = jdbcTemplate.query(
                "select id from POST_LIKE where user_id = ? and post_id = ?", idRowMapper(), userId, postId);
        return !result.isEmpty();
    }

    public Optional<Integer> findLikeByUserIdAndPostId(int userId, int postId) {
        return jdbcTemplate.query(
                "select id from POST_LIKE where is_deleted = false and user_id = ? and post_id = ?",
                idRowMapper(), userId, postId).stream().findAny();
    }

    public void unLike(int likeId) {
        jdbcTemplate.update(
                "update POST_LIKE set is_deleted = true where id = ?", likeId);
    }

    public void addLike(int userId, int postId) {
        jdbcTemplate.update(
        "insert into POST_LIKE(user_id, post_id) values (?, ?) " +
                "on DUPLICATE KEY update is_deleted = false",
                userId, postId);
    }

    private RowMapper<Integer> idRowMapper(){
        return (rs, rowNum) -> rs.getInt("id");
    }



}
