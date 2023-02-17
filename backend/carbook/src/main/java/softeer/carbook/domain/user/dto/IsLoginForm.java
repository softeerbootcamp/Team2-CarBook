package softeer.carbook.domain.user.dto;

public class IsLoginForm {
    private final boolean isLogin;

    public IsLoginForm(boolean isLogin){
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
