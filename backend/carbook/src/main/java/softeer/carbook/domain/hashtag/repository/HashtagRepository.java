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
        List<Hashtag> hashtags = jdbcTemplate.query("SELECT h.id, h.tag FROM HASHTAG h WHERE tag = ?", tagRowMapper(), tag);
        return hashtags.stream()
                .findAny()
                .orElseThrow(() -> new HashtagNotExistException());
    }

    public List<Hashtag> searchHashtagByPrefix(String keyword) {
        return jdbcTemplate.query("SELECT h.id, h.tag FROM HASHTAG h WHERE tag LIKE '" + keyword + "%'", tagRowMapper());
    }

    private RowMapper<Hashtag> tagRowMapper() {
        return (rs, rowNum) -> new Hashtag(
                rs.getInt("id"),
                rs.getString("tag")
        );
    }
}
