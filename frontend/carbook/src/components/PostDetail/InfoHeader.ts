import { Component } from "@/core";
import profile from "@/assets/icons/postdetail_profile.svg";
import { push } from "@/utils/router/navigate";
import { getClosest } from "@/utils";
import { basicAPI } from "@/api";

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
        console.log("삭제요청");
        const postid = location.pathname.split("/").slice(-1)[0];
        this.deletePost(postid);
        return;
      }

      if (getClosest(target, ".info-profile")) {
        push(`/profile/${nickname}`);
      }
    });
  }

  async deletePost(postid: string) {
    const modal = document.body.querySelector(".alert-modal") as HTMLElement;
    await basicAPI
      .delete(`/api/post/${postid}`)
      .then((res) => {
        console.log(res.data.message);
        alert("게시글 삭제에 성공하셨습니다");
        // showErrorModal(modal, "게시글 삭제에 성공했습니다");
        push(`/`);
      })
      .catch((err) => {
        console.log(err);
        alert("게시글 삭제에 실패하셨습니다");
        // showErrorModal(modal, "게시글 삭제에 실패했습니다");
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

function showErrorModal(modal: HTMLElement, errorMessage: string): void {
  if (modal.classList.contains("FadeInAndOut")) return;
  modal.innerHTML = errorMessage;
  modal.classList.add("pink");
  modal.classList.toggle("FadeInAndOut");
  setTimeout(() => {
    modal.classList.toggle("FadeInAndOut");
    modal.classList.add("pink");
  }, 2000);
}
