import { basicAPI } from "@/api";
import { Component } from "@/core";
import { push } from "../../utils/router/navigate";

export default class Menu extends Component {
  template(): string {
    return /*html */ `
      <div class ='header-info-menu'>
        <ul class = 'info-menu-items hidden'>
          <h3>설정</h3>
          <li class ='modify-nickname'>닉네임 변경</li>
          <li class ='modify-password'>비밀번호 변경</li>
          <li class ='logout'>로그아웃</li>
        </ul>
      </div>`;
  }
  mounted(): void {
    const { nickname } = this.props;
    /**todo
     * 1. 닉네임 변경 누르면 닉네임 변경 모달창
     * 2. 비밀번호 변경 누르면 비밀번호 변경 모달창
     */
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const li = target.closest("li");
      const modal = document.body.querySelector(".alert-modal") as HTMLElement;

      if (!li) return;
      if (li.classList.contains("logout")) {
        this.doLogout(modal);
        return;
      }

      const modifyModal = document.body.querySelector(
        ".modify-modal"
      ) as HTMLElement;

      if (li.classList.contains("modify-nickname")) {
        const nicknameInput = modifyModal.querySelector(
          ".modify-modal-form-nickname input"
        ) as HTMLInputElement;
        nicknameInput.value = nickname;
        nicknameInput.focus();
        modifyModal.classList.add("nickname");
      }

      if (li.classList.contains("modify-password")) {
        modifyModal.classList.add("password");
      }

      modifyModal.classList.toggle("FadeInAndOut");
      const menu = this.$target.querySelector(
        ".info-menu-items"
      ) as HTMLElement;
      menu.classList.toggle("FadeInAndOut");
    });
  }

  async doLogout(modal: HTMLElement) {
    const LOGOUTDELAY = 2300;
    await basicAPI
      .post(`/api/profile/logout`)
      .then(() => {
        showErrorModal(modal, "로그아웃에 성공하셨습니다");
        setTimeout(() => {
          push("/");
        }, LOGOUTDELAY);
      })
      .catch((error) => error);
  }
  setEvent(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const menu = target.querySelector(".info-menu-items") as HTMLElement;
      if (!menu) return;
      menu.classList.toggle("FadeInAndOut");
    });
  }
}

function showErrorModal(modal: HTMLElement, errorMessage: string): void {
  if (modal.classList.contains("FadeInAndOut")) return;
  modal.innerHTML = errorMessage;
  modal.classList.toggle("blue");
  modal.classList.toggle("FadeInAndOut");
  setTimeout(() => {
    modal.classList.toggle("FadeInAndOut");
    modal.classList.toggle("blue");
  }, 2000);
}
