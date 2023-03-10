package softeer.carbook.domain.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.IdNotExistException;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.NicknameNotExistException;
import softeer.carbook.domain.user.model.User;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUser(User user) {
        jdbcTemplate.update("insert into `USER`(email, password, nickname) values(?, ?, ?)",
                user.getEmail(),
                user.getPassword(),
                user.getNickname()
        );
    }

    public void modifyNickname(String nickname, String newNickname) {
        jdbcTemplate.update("update `USER` set nickname = ? where nickname = ?",
                newNickname,
                nickname
        );
    }

    public void modifyPassword(int user_id, String newPassword) {
        jdbcTemplate.update("update `USER` set password = ? where id = ?",
                newPassword,
                user_id
        );
    }

    public boolean isEmailDuplicated(String email) {
        List<User> result = jdbcTemplate.query("select u.id, u.email, u.nickname, u.password from `USER` u where email = ?", userRowMapper(), email);
        return !result.isEmpty();
    }

    public boolean isNicknameDuplicated(String nickname) {
        List<User> result = jdbcTemplate.query("select u.id, u.email, u.nickname, u.password from `USER` u where nickname = ?", userRowMapper(), nickname);
        return !result.isEmpty();
    }

    public User findUserById(int id){
        List<User> result = jdbcTemplate.query("select u.id, u.email, u.nickname, u.password from `USER` u where id = ?", userRowMapper(), id);
        return result.stream().findAny().orElseThrow(
                () -> new IdNotExistException()
        );
    }

    public User findUserByEmail(String email){
        List<User> result = jdbcTemplate.query("select u.id, u.email, u.nickname, u.password from `USER` u where email = ?", userRowMapper(), email);
        return result.stream().findAny().orElseThrow(
                () -> new LoginEmailNotExistException()
        );
    }

    public User findUserByNickname(String nickname){
        List<User> result = jdbcTemplate.query("select u.id, u.email, u.nickname, u.password from `USER` u where nickname = ?", userRowMapper(), nickname);
        return result.stream().findAny().orElseThrow(
                () -> new NicknameNotExistException()
        );
    }

    public List<String> getFollowingNicknames(String nickname){
        return jdbcTemplate.queryForList("select u2.nickname from FOLLOW f " +
                "INNER JOIN `USER` u1 ON f.follower_id = u1.id " +
                "INNER JOIN `USER` u2 ON f.following_id = u2.id " +
                "where u1.nickname = ? and f.is_deleted = false" , String.class, nickname);
    }

    public List<String> getFollowerNicknames(String nickname){
        return jdbcTemplate.queryForList("select u2.nickname from FOLLOW f " +
                "INNER JOIN `USER` u1 ON f.following_id = u1.id " +
                "INNER JOIN `USER` u2 ON f.follower_id = u2.id " +
                "where u1.nickname = ? and f.is_deleted = false" , String.class, nickname);
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("nickname"),
                rs.getString("password")
        );
    }

    /* deprecated
    public String findEmailByNickname(String nickname) {
        List<String> result = jdbcTemplate.query("select email from USER where nickname = ?", emailRowMapper(), nickname);
        return result.stream().findAny().orElseThrow(
                () -> new NicknameNotExistException("ERROR: Nickname not exist")
        );
    }

    public int findUserIdByNickname(String nickname) {
        List<Integer> result = jdbcTemplate.query("select id from USER where nickname = ?", idRowMapper(), nickname);
        return result.stream().findAny().orElseThrow(
                () -> new idNotExistException("ERROR: Id not exist")
        );
    }

    private RowMapper<String> emailRowMapper(){
        return (rs, rowNum) -> rs.getString("email");
    }

    private RowMapper<Integer> idRowMapper(){
        return (rs, rowNum) -> rs.getInt("id");
    }
    */

}
