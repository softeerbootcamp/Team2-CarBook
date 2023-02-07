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

    public List<Post> searchByHashtags(List<Integer> hashtagIds, int size, int index) {
        String whereStatement = createWhereStatement(hashtagIds);
        String query = "SELECT p.id, p.user_id, p.create_date, p.update_date, p.content " +
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

    /* deprecated
    public List<Post> getPosts(int size, int index) {
        return jdbcTemplate.query("SELECT * from POST ORDER BY create_date DESC LIMIT ?, ?",
                postRowMapper(), index, size);
    }

    public List<Post> getPostsByUserId(int size, int index, int userId){
        List<Post> posts = jdbcTemplate.query("select * from POST where user_id = ? ORDER BY create_date DESC LIMIT ?, ?",
                postRowMapper(), userId, index, size);
        return posts;
    }

    public List<Post> getFollowingPosts(int size, int index, int followerId){
        List<Post> posts = jdbcTemplate.query("select * from POST where user_id in (select following from FOLLOW where follower = ?) ORDER BY create_date DESC LIMIT ?, ?",
                postRowMapper(), followerId, index, size);
        return posts;
    }

     */

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> new Post(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getTimestamp("create_date"),
                rs.getTimestamp("update_date"),
                rs.getString("content")
        );
    }

}
