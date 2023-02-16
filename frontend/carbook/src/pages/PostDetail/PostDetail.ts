import { Component } from "@/core";
import "./PostDetail.scss";
import backButton from "@/assets/icons/backButton.svg";
import { InfoContents, InfoHeader, Footer } from "@/components/PostDetail";

export default class PostDetailPage extends Component {
  setup(): void {
    // const postid = location.pathname.split("/").slice(-1)[0];
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      if (!target.classList.contains("info-menu")) return;
      const menuItems = target.querySelector(".info-menu-items") as HTMLElement;
      menuItems?.classList.toggle("FadeInAndOut");
    });

    this.state = {
      isMyPost: true,
      nickname: "유저닉네임",
      imageUrl: "이미지 url",
      likeCount: 23, // 좋아요 개수
      like: false,
      createDate: "2023.02.01. 11:03", // 작성 일자
      updateDate: "2023.02.02. 03:24", // 최근 수정 일자
      type: "차 종류",
      model: "차 모델",
      keywords: [
        { id: "1", category: "hashtag", tag: "이름" },
        { id: "2", category: "type", tag: "이름" },
        { id: "3", category: "model", tag: "이름" },
      ],
      content: "글 내용",
    };
  }

  template(): string {
    return /*html*/ `
    <div class ='postdetail-container'>
      <div class = 'header'>
      </div>

      <div class ='postdetail-info'>
        <div class ='info-header'>
        </div>
        <div class = 'info-contents'>
        </div>
      </div>
      
      <footer class ='postdetail-footer'>
      </footer>

      <div class = 'back-button'> <img class = 'backbutton' src = "${backButton}"/> </div>
    </div>
    `;
  }
  mounted(): void {
    const infoHeader = this.$target.querySelector(
      ".info-header"
    ) as HTMLElement;
    const infoContents = this.$target.querySelector(
      ".info-contents"
    ) as HTMLElement;
    const footer = this.$target.querySelector(
      ".postdetail-footer"
    ) as HTMLElement;
    new Footer(footer, {
      like: this.state.like,
      likeCount: this.state.likeCount,
      createDate: this.state.createDate,
      updateDate: this.state.updateDate,
    });
    new InfoContents(infoContents, {
      type: this.state.type,
      model: this.state.model,
      content: this.state.content,
    });
    new InfoHeader(infoHeader, {
      nickname: this.state.nickname,
      isMyPost: this.state.isMyPost,
    });
  }
}
