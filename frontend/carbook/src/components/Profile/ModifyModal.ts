import { Component } from "@/core";

export default class ModifyModal extends Component {
  template(): string {
    return /*html*/ `
        <div class = 'title'>회원 정보
          <div class = 'cancel-button'></div>
        </div>
        <div class ='modify-modal-form'>
          <form>
            <div class ='modify-modal-form-nickname'>
              <h3 class = 'form-label'>닉네임</h3>
              <input class ='input-box' placeholder='현재 닉네임'/>
              <div class ='eyes slash'></div>
            </div>
            <div class ='modify-modal-form-password'>
              <h3 class = 'form-label'>비밀번호</h3>
              <input type ='password' class ='input-box' placeholder='기존 비밀번호'/>
              <div class ='eyes slash'></div>
            </div>
            <div class ='modify-modal-form-modify-password'>
              <h3 class = 'form-label'>변경할 비밀번호</h3>
              <input type ='password' class ='input-box' placeholder='변경할 비밀번호'/>
              <div class ='eyes slash'></div>
            </div>
            <div class ='modify-modal-form-password-confirm'>
              <h3 class = 'form-label'>변경할 비밀번호 확인</h3>
              <input type ='password' class ='input-box'/>
              <div class ='eyes slash'></div>
            </div>
            <div class ='modify-modal-footer'><button class ='modify-button'>변경</button> </div>
          </form>
        </div>
      `;
  }

  setEvent(): void {
    if (this.$target.classList.contains("once")) return;
    this.$target.classList.add("once");
    this.$target.addEventListener("click", (e: Event) => {
      e.preventDefault();
      const target = e.target as HTMLElement;
      const cancelButton = target.closest(".cancel-button");
      const eyes = target.closest(".eyes");

      // 비밀번호 보이기 / 안보이기
      if (eyes) {
        eyes.classList.toggle("slash");
        const inputBox = eyes.parentElement?.querySelector(
          "input"
        ) as HTMLInputElement;
        eyes.classList.contains("slash")
          ? (inputBox.type = "password")
          : (inputBox.type = "text");
        return;
      }

      //취소버튼 누를때 모달창 닫기
      if (!cancelButton) return;
      const modifyModal = this.$target.closest(".modify-modal") as HTMLElement;
      modifyModal.classList.toggle("FadeInAndOut");
      setTimeout(() => {
        modifyModal.classList.remove("nickname");
        modifyModal.classList.remove("password");
      }, 1000);
    });
  }
}
