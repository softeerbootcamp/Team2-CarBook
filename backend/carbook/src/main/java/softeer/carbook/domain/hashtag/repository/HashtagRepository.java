package softeer.carbook.domain.hashtag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.hashtag.exception.HashtagNotExistException;
import softeer.carbook.domain.hashtag.model.Hashtag;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HashtagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HashtagRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Hashtag findHashtagByName(String tag) {
        List<Hashtag> hashtags = jdbcTemplate.query("select * from HASHTAG where tag = ?", tagRowMapper(), tag);
        return hashtags.stream()
                .findAny()
                .orElseThrow(() -> new HashtagNotExistException());
    }

    private RowMapper<Hashtag> tagRowMapper() {
        return (rs, rowNum) -> new Hashtag(
                rs.getInt("id"),
                rs.getString("tag")
        );
    }
}
