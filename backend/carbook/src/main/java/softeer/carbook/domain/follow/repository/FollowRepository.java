package softeer.carbook.domain.follow.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
