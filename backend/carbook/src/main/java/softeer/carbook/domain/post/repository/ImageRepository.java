package softeer.carbook.domain.post.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.post.model.Image;

import javax.sql.DataSource;

@Repository
public class ImageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public Image getImageByPostId(int postId){
        Image image = jdbcTemplate.queryForObject("select * from IMAGE where post_id = ?",
                imageRowMapper(), postId);
        return image;
    }

    private RowMapper<Image> imageRowMapper(){
        return (rs, rowNum) -> {
            Image image = new Image(
                    rs.getInt("post_id"),
                    rs.getString("image_url")
            );
            return image;
        };
    }
}
