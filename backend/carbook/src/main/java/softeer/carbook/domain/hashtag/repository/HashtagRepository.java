package softeer.carbook.domain.hashtag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.hashtag.exception.HashtagNotExistException;
import softeer.carbook.domain.hashtag.model.Hashtag;
import softeer.carbook.domain.hashtag.model.Type;

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
        List<Hashtag> hashtags = jdbcTemplate.query("SELECT h.id, h.tag FROM HASHTAG h WHERE tag = ?", hashtagRowMapper(), tag);
        return hashtags.stream()
                .findAny()
                .orElseThrow(() -> new HashtagNotExistException());
    }

    public List<Hashtag> searchHashtagByPrefix(String keyword) {
        return jdbcTemplate.query("SELECT h.id, h.tag FROM HASHTAG h WHERE tag LIKE '" + keyword + "%'", hashtagRowMapper());
    }

    public List<Type> searchTypeByPrefix(String keyword) {
        return jdbcTemplate.query("SELECT t.id, t.tag FROM Type t WHERE tag LIKE '" + keyword + "%'", typeRowMapper());
    }

    private RowMapper<Hashtag> hashtagRowMapper() {
        return (rs, rowNum) -> new Hashtag(
                rs.getInt("id"),
                rs.getString("tag")
        );
    }

    private RowMapper<Type> typeRowMapper() {
        return (rs, rowNum) -> new Type(
                rs.getInt("id"),
                rs.getString("tag")
        );
    }
}
