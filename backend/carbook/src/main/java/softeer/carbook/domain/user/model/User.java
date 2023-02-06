package softeer.carbook.domain.user.model;
public class User {
    private final String email;
    private final String password;
    private final String nickname;


    public User(String email, String nickname, String password) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
