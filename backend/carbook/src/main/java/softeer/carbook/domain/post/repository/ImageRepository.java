package softeer.carbook.domain.post.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public Image getImageByPostId(int postId){
        return jdbcTemplate.queryForObject("select * from IMAGE where post_id = ?",
                imageRowMapper(), postId);
    }

    public List<Image> getImagesOfRecentPosts(int size, int index) {
        return jdbcTemplate.query("SELECT img.post_id, img.image_url " +
                        "FROM POST AS p INNER JOIN IMAGE AS img ON p.id = img.post_id " +
                        "ORDER BY p.create_date DESC LIMIT ?, ?",
                imageRowMapper(), index, size);
    }

    public List<Image> getImagesOfRecentFollowingPosts(int size, int index, int followerId){
        return jdbcTemplate.query("SELECT img.post_id, img.image_url " +
                "FROM POST AS p, IMAGE AS img, FOLLOW AS f " +
                        "where f.follower_id = ?" +
                        "and f.following_id = p.user_id " +
                        "and p.id = img.post_id " +
                "ORDER BY p.create_date DESC LIMIT ?, ?",
                imageRowMapper(), followerId, index, size);
    }

    public List<Image> findImagesByUserId(int id) {
        List<Image> images = jdbcTemplate.query(
                "select IMAGE.post_id, IMAGE.image_url from POST INNER JOIN IMAGE " +
                        "ON POST.id = IMAGE.post_id " +
                        "WHERE POST.user_id = ? " +
                        "ORDER BY create_date ",
                imageRowMapper(), id);
        return images;
    }

    public List<Image> findImagesByNickName(String profileUserNickname) {
        List<Image> images = jdbcTemplate.query(
                "select IMAGE.post_id, IMAGE.image_url from USER, POST, IMAGE " +
                        "WHERE USER.id = POST.user_id and POST.id = IMAGE.post_id " +
                        "and USER.nickname = ?" +
                        "ORDER BY create_date ",
                imageRowMapper(), profileUserNickname);
        return images;
    }

    private RowMapper<Image> imageRowMapper(){
        return (rs, rowNum) -> new Image(
                rs.getInt("post_id"),
                rs.getString("image_url")
        );
    }
}
