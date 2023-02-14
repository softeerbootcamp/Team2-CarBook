import { Component } from "@/core";
import FollowButton from "./FollowButton";
import Menu from "./Menu";
export default class HeaderInfo extends Component {
  template(): string {
    const { nickname, email } = this.props;
    return /*html*/ `
      <div class = 'header-info-header'>
        <div class ='user-nickname'>${nickname ?? ""}</div>
        <div class ='modify-status'></div>
      </div>
      <div class ='user-email'>${email ?? ""}</div>
      `;
  }

  mounted(): void {
    const { isMyProfile, isFollow, nickname } = this.props;
    const modify_status = this.$target.querySelector(
      ".modify-status"
    ) as HTMLElement;

    if (isMyProfile === undefined) return;
    isMyProfile
      ? new Menu(modify_status, { nickname })
      : new FollowButton(modify_status, { isFollow });
  }
}
