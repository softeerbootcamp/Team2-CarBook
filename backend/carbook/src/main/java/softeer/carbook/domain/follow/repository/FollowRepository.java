package softeer.carbook.domain.follow.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class FollowRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FollowRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(); }

    public List<Integer> getFollowerIds(int followingId){
        return jdbcTemplate.query("select follower_id from FOLLOW where following_id = ?", followerIdRowMapper(), followingId);
    }

    public List<Integer> getFollowingIds(int followerId){
        return jdbcTemplate.query("select following_id from FOLLOW where follower_id = ?", followingIdRowMapper(), followerId);
    }

    public int getFollowerCount(int followingId){
        return jdbcTemplate.queryForObject("select count(*) from FOLLOW where following_id = ?", Integer.class, followingId);
    }

    public int getFollowingCount(int followerId){
        return jdbcTemplate.queryForObject("select count(*) from FOLLOW where follower_id = ?", Integer.class, followerId);
    }

    public boolean isFollow(int id, int followingId) {
        return jdbcTemplate.queryForObject(
                "select EXISTS (select * from FOLLOW where follower_id = ? and following_id = ? limit 1) as success",
                Integer.class, id, followingId) != 0;
    }

    public Optional<Integer> findFollowId(int followerId, int followingId) {
        return jdbcTemplate.query(
                "select id from FOLLOW where follower_id = ? and following_id = ?",
                idRowMapper(), followerId, followingId).stream().findAny();
    }

    public void unFollow(int followId) {
        jdbcTemplate.update(
                "delete from FOLLOW where id = ?", followId);
    }

    private RowMapper<Integer> idRowMapper(){
        return (rs, rowNum) -> rs.getInt("id");
    }

    private RowMapper<Integer> followerIdRowMapper(){
        return (rs, rowNum) -> {
            Integer followerId = rs.getInt("follower_id");
            return followerId;
        };
    }

    private RowMapper<Integer> followingIdRowMapper(){
        return (rs, rowNum) -> {
            Integer followingId = rs.getInt("following_id");
            return followingId;
        };
    }



}
