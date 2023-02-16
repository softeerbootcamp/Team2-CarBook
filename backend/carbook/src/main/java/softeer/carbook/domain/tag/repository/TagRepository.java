package softeer.carbook.domain.tag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.tag.exception.HashtagNotExistException;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.model.Type;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Hashtag findHashtagByName(String tag) {
        List<Hashtag> hashtags = jdbcTemplate.query("SELECT h.id, h.tag FROM HASHTAG h WHERE tag = ?", hashtagRowMapper(), tag);
        return hashtags.stream()
                .findAny()
                .orElseThrow(HashtagNotExistException::new);
    }

    public List<Type> findTypeById(int id) {
        return jdbcTemplate.query(
                "select id, tag from TYPE where id = ?",
                typeRowMapper(), id
        );
    }

    public List<Model> findModelByModelId(int modelId) {
        return jdbcTemplate.query(
                "SELECT id, type_id, tag FROM MODEL " +
                        "WHERE id = ?",
                modelRowMapper(), modelId);
    }

    public List<String> findHashtagsByPostId(int postId) {
        return jdbcTemplate.query(
                "SELECT h.tag FROM HASHTAG h " +
                        "INNER JOIN POST_HASHTAG p_h " +
                        "ON h.id = p_h.tag_id " +
                        "WHERE p_h.post_id = ?",
                hashtagStringRowMapper(), postId);
    }

    public List<Type> searchTypeByPrefix(String keyword) {
        return jdbcTemplate.query("SELECT t.id, t.tag FROM TYPE t WHERE tag LIKE '" + convertWildCharToRealChar(keyword) + "%'", typeRowMapper());
    }

    public List<Model> searchModelByPrefix(String keyword) {
        return jdbcTemplate.query("SELECT m.id, m.type_id, m.tag FROM MODEL m WHERE tag LIKE '" + convertWildCharToRealChar(keyword) + "%'", modelRowMapper());
    }

    public List<Hashtag> searchHashtagByPrefix(String keyword) {
        return jdbcTemplate.query("SELECT h.id, h.tag FROM HASHTAG h WHERE tag LIKE '" + convertWildCharToRealChar(keyword) + "%'", hashtagRowMapper());
    }

    public List<Type> findAllTypes() {
        return jdbcTemplate.query("SELECT t.id, t.tag FROM TYPE t", typeRowMapper());
    }

    public List<Model> findAllModels() {
        return jdbcTemplate.query("SELECT m.id, m.type_id, m.tag FROM MODEL m", modelRowMapper());
    }

    public Model findModelByName(String tag){
        return jdbcTemplate.query("SELECT m.id, m.type_id, m.tag FROM MODEL m WHERE m.tag = ?", modelRowMapper(), tag).get(0);
    }

    public String convertWildCharToRealChar(String keyword) {
        return keyword.replaceAll("%", "[%]").replaceAll("_", "[_]");
    }

    public int addHashtag(Hashtag hashtag){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO HASHTAG (tag) values(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, hashtag.getTag());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void addPostHashtag(int postId, int tagId){
        jdbcTemplate.update("insert into POST_HASHTAG(post_id, tag_id) values(?, ?)",
                postId, tagId
        );
    }

    public void deletePostHashtags(int postId){
        jdbcTemplate.update("delete from POST_HASHTAG where post_id=?",postId);
    }

    private RowMapper<Type> typeRowMapper() {
        return (rs, rowNum) -> new Type(
                rs.getInt("id"),
                rs.getString("tag")
        );
    }

    private RowMapper<Model> modelRowMapper() {
        return (rs, rowNum) -> new Model(
                rs.getInt("id"),
                rs.getInt("type_id"),
                rs.getString("tag")
        );
    }

    private RowMapper<Hashtag> hashtagRowMapper() {
        return (rs, rowNum) -> new Hashtag(
                rs.getInt("id"),
                rs.getString("tag")
        );
    }

    private RowMapper<String> hashtagStringRowMapper(){
        return ((rs, rowNum) -> rs.getString("tag"));
    }

}
