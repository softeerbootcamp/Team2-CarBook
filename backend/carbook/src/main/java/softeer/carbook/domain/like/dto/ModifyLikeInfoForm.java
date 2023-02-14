package softeer.carbook.domain.like.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ModifyLikeInfoForm {
    @NotBlank
    private final int postId;

    // json 파싱 문제 때문에 빈 생성자를 생성해 주어야 함..

    public ModifyLikeInfoForm() {
        postId = 0;
    }

    public ModifyLikeInfoForm(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}
