import { Component } from "@/core";

export default class Menu extends Component {
  template(): string {
    return /*html */ `
      <div class ='header-info-menu'>
        <ul class = 'info-menu-items hidden'>
          <h3>설정</h3>
          <li>닉네임 변경</li>
          <li>비밀번호 변경</li>
          <li>로그아웃</li>
        </ul>
      </div>`;
  }
  mounted(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const menu = target.querySelector(".info-menu-items");
      if (!menu) return;
      menu.classList.toggle("hidden");
    });
  }
}
