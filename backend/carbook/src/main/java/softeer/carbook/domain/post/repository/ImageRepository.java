package softeer.carbook.domain.post.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import softeer.carbook.domain.post.model.Image;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public Image getImageByPostId(int postId){
        return jdbcTemplate.queryForObject("select img.post_id, img.image_url from IMAGE img where post_id = ?",
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
                        "where f.is_deleted = false " +
                        "and f.follower_id = ? " +
                        "and f.following_id = p.user_id " +
                        "and p.id = img.post_id " +
                "ORDER BY p.create_date DESC LIMIT ?, ?",
                imageRowMapper(), followerId, index, size);
    }

    public List<Image> findImagesByUserId(int id) {
        return jdbcTemplate.query(
                "select IMAGE.post_id, IMAGE.image_url from POST INNER JOIN IMAGE " +
                        "ON POST.id = IMAGE.post_id " +
                        "WHERE POST.user_id = ? " +
                        "ORDER BY create_date DESC",
                imageRowMapper(), id);
    }

    public List<Image> findImagesByNickName(String profileUserNickname) {
        return jdbcTemplate.query(
                "select IMAGE.post_id, IMAGE.image_url from USER, POST, IMAGE " +
                        "WHERE USER.id = POST.user_id and POST.id = IMAGE.post_id " +
                        "and USER.nickname = ?" +
                        "ORDER BY create_date DESC",
                imageRowMapper(), profileUserNickname);
    }

    public List<Image> getImagesOfRecentPostsByTags(String[] tagNames, int size, int index) {
        String conditionalStatement = createTagNameConditionalStatement(tagNames);
        String query = "SELECT img.post_id, img.image_url " +
                "FROM POST p " +
                "INNER JOIN POST_HASHTAG ph ON p.id = ph.post_id " +
                "INNER JOIN HASHTAG h ON ph.tag_id = h.id "+
                "INNER JOIN IMAGE img ON p.id = img.post_id " +
                "WHERE (" + conditionalStatement + ") ORDER BY p.create_date DESC LIMIT ?, ?";

        return jdbcTemplate.query(query, imageRowMapper(), index, size);
    }

    private String createTagNameConditionalStatement(String[] tagNames) {
        StringBuilder conditionalStatement = new StringBuilder();
        for (String tagName: tagNames) {
            conditionalStatement.append("h.tag = '").append(tagName).append("'");
            conditionalStatement.append((" OR "));
        }

        return conditionalStatement.substring(0, conditionalStatement.length() - 4);
    }

    public void addImage(Image image) {
        jdbcTemplate.update("insert into IMAGE(post_id, image_url) values(?, ?)",
                image.getPostId(),
                image.getImageUrl()
        );
    }

    private RowMapper<Image> imageRowMapper(){
        return (rs, rowNum) -> new Image(
                rs.getInt("post_id"),
                rs.getString("image_url")
        );
    }

}
