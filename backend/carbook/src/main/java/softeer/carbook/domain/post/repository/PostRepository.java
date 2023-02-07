package softeer.carbook.domain.post.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.post.model.Post;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Post> getPosts(int size, int index) {
        return jdbcTemplate.query("select * from POST ORDER BY create_date DESC LIMIT ?, ?",
                postRowMapper(), index, size);
    }

    public List<Post> searchByHashtags(List<Integer> hashtagIds, int size, int index) {
        String whereStatement = createWhereStatement(hashtagIds);
        String query = "select p.id, p.user_id, p.create_date, p.update_date, p.content " +
                "FROM POST AS p " +
                "INNER JOIN POST_HASHTAG AS ph " +
                "ON p.id = ph.post_id WHERE " + whereStatement + " ORDER BY p.create_date DESC LIMIT ?, ?";

        return jdbcTemplate.query(query, postRowMapper(), index, size);
    }

    private String createWhereStatement(List<Integer> hashtagIds) {
        StringBuilder whereStatement = new StringBuilder();
        for (int id : hashtagIds) {
            whereStatement.append("ph.tag_id = ").append(id);
            whereStatement.append((" OR "));
        }

        return whereStatement.substring(0, whereStatement.length() - 4);
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("create_date"),
                    rs.getTimestamp("update_date"),
                    rs.getString("content")
            );
            return post;
        };
    }

}
