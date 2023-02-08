package softeer.carbook.domain.user.model;
public class User {
    private final int id;
    private final String email;
    private final String password;
    private final String nickname;


    public User(int id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
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
