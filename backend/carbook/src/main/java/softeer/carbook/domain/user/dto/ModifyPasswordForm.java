package softeer.carbook.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ModifyPasswordForm {
    private final String password;
    @Size(max = 16, message = "비밀번호는 16자 이하여야 합니다.")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private final String newPassword;

    public ModifyPasswordForm(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
