import { Component } from "@/core";
import profile from "@/assets/icons/postdetail_profile.svg";

export default class InfoHeader extends Component {
  template(): string {
    const { isMyPost, nickname } = this.props;
    return /*html*/ `
    <div class='info-profile'>
      <img class ='user-img' src = "${profile}"/>
      <h2 class = 'user-nickname'>${nickname}</h2>
    </div>
    <div class ='info-menu ${isMyPost ? "" : "hidden"}'>
      <ul class = 'info-menu-items'>
        <li>메뉴</li>
        <li>수정</li>
        <li>삭제</li>
      </ul>
    </div>
    `;
  }
}
