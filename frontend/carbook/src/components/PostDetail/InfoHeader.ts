import { Component } from "@/core";
import profile from "@/assets/icons/postdetail_profile.svg";
import { push } from "@/utils/router/navigate";
import { getClosest } from "@/utils";

export default class InfoHeader extends Component {
  setup(): void {
    const { nickname } = this.props;
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;

      console.log(target);
      if (target.classList.contains("modify-post")) {
        const postid = location.pathname.split("/").slice(-1)[0];
        push(`/post/${postid}/edit`);
        return;
      }

      if (target.classList.contains("delete-post")) {
        // const postid = location.pathname.split("/").slice(-1)[0];
        // push(`/post/${postid}/edit`);
        // return;
        console.log("삭제요청");
        return;
      }

      if (getClosest(target, ".info-profile")) {
        push(`/profile/${nickname}`);
      }
    });
  }
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
        <li class = 'modify-post'>수정</li>
        <li class = 'delete-post'>삭제</li>
      </ul>
    </div>
    `;
  }
}
