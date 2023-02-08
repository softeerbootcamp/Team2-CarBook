package softeer.carbook.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewNickNameForm {
    @Size(max = 16, message = "닉네임은 16자 이하여야 합니다.")
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private final String newNickname;

    public NewNickNameForm(String newNickname) {
        this.newNickname = newNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }
}
