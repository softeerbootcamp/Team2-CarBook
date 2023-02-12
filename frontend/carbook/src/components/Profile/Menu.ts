import { Component } from "@/core";

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
    /**todo
     * 1. 닉네임 변경 누르면 닉네임 변경 모달창
     * 2. 비밀번호 변경 누르면 비밀번호 변경 모달창
     * 3. 로그아웃 누르면 로그아웃
     */
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const li = target.closest("li");
      if (!li) return;
      document.body.querySelector(".modify-modal")?.classList.toggle("hidden");
      const menu = this.$target.querySelector(
        ".info-menu-items"
      ) as HTMLElement;
      menu.classList.toggle("hidden");
    });
  }
  setEvent(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const menu = target.querySelector(".info-menu-items") as HTMLElement;
      if (!menu) return;
      menu.classList.toggle("hidden");
    });
  }
}
