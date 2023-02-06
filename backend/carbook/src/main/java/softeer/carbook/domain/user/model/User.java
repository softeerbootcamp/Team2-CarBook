package softeer.carbook.domain.user.model;
public class User {
    private final String email;
    private final String nickname;
    private final String password;

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
