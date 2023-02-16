import { Component } from "@/core";
import "./PostDetail.scss";
import backButton from "@/assets/icons/backButton.svg";
import { InfoContents, InfoHeader, Footer } from "@/components/PostDetail";
import { basicAPI } from "@/api";

export default class PostDetailPage extends Component {
  async setup() {
    this.state.isloading = true;
    const postid = location.pathname.split("/").slice(-1)[0];
    await this.fetchPostDefail(postid);
    if (this.$target.classList.contains("once")) return;
    this.$target.classList.add("once");
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;

      if (!target.classList.contains("info-menu")) {
        const menuItems = target.querySelector(
          ".info-menu-items"
        ) as HTMLElement;
        menuItems?.classList.toggle("FadeInAndOut");
      }

      if (target.closest(".back-button")) {
        history.back();
      }
    });
  }

  async fetchPostDefail(postId: string) {
    const data = await basicAPI
      .get(`/api/post?postId=${postId}`)
      .then((response) => response.data)
      .catch((error) => {
        const errorMessage = error.response.data.message;
        console.log(errorMessage);
      });
    this.setState({
      ...this.state,
      nickname: data?.nickname,
      content: data?.content,
      createDate: data?.createDate,
      hashtags: data?.hashtags,
      imageUrl: data?.imageUrl,
      like: data?.like,
      likeCount: data?.likeCount,
      model: data?.model,
      myPost: data?.myPost,
      type: data?.type,
      updateDate: data?.updateDate,
      isloading: false,
    });
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

  render(): void {
    if (this.state?.isloading) return;
    this.$target.innerHTML = this.template();
    this.setEvent();
    this.mounted();
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
      hashtags: this.state.hashtags,
    });
    new InfoHeader(infoHeader, {
      nickname: this.state.nickname,
      isMyPost: this.state.myPost,
    });
  }
}
