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

      /**
       * 닉네임 수정
       * 모달창의 닉네임 창에 기존 닉네임 입력
       * 입력 변경버튼 누르면
       * 1. 원래 닉네임이랑 같은지 체크
       * 2. 다른 유저의 닉네임과 중복 체크
       */
      if (li.classList.contains("modify-nickname")) {
        const nicknameInput = modifyModal.querySelector(
          ".modify-modal-form-nickname input"
        ) as HTMLInputElement;
        nicknameInput.value = nickname;
        nicknameInput.focus();
        modifyModal.classList.add("nickname");
      }

      /**
       * 비밀번호 수정
       * 기존 비밀번호
       * 입력 변경버튼 누르면 전송
       * 1. 기존 비밀번호와 같은지 체크
       * 2. 기존 비밀번호가 맞는지 체크
       * 3. 변경 성공하면 모달창 닫음
       */
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
