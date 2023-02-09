import { Component } from "@/core";

export default class HeaderInfo extends Component {
  template(): string {
    const { nickname, email } = this.props;
    return /*html*/ `
      <div class = 'header-info-header'>
        <div class ='user-nickname'>${nickname}</div>
        <div class ='modify-status'></div>
      </div>
      <div class ='user-email'>${email}</div>
      `;
  }

  mounted(): void {
    const { isMyProfile, isFollow } = this.props;
    const modify_status = this.$target.querySelector(
      ".modify-status"
    ) as HTMLElement;

    isMyProfile
      ? new Menu(modify_status)
      : new FollowButton(modify_status, { isFollow });
  }
}

class FollowButton extends Component {
  template(): string {
    const { isFollow } = this.props;
    return `<button class = 'follow-button'>${
      isFollow ? "언팔로우" : "팔로우"
    }</button>`;
  }
}

class Menu extends Component {
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
