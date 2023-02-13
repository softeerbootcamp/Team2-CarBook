import { Component } from "@/core";
import "./Profile.scss";

import {
  HeaderInfo,
  HeaderContents,
  PostLists,
  Followlists,
} from "@/components/Profile";

import { basicAPI } from "@/api";

export default class ProfilePage extends Component {
  setup(): void {
    //path에서 닉네임 가져오기
    // console.log(location.pathname.split("/").slice(-1)[0]);
    const urlnickname = location.pathname.split("/").slice(-1)[0];
    this.setState({ nickname: urlnickname });
    this.fetchProfilePage(urlnickname);
  }

  async fetchProfilePage(urlnickname: string) {
    const data = await basicAPI
      .get(`/api/profile?nickname=${urlnickname}`)
      .then((response) => response.data)
      .catch((error) => error);
    this.setState({
      isMyProfile: data.myProfile,
      isFollow: data.follow,
      nickname: data.nickname,
      email: data.email,
      profileMode: "posts",
      images: data.images,
      follower: data.follower,
      following: data.following,
    });
  }

  async receiveFollower() {}

  template(): string {
    return /*html*/ `
    <div class = 'profile__container'>
      <header>
        <div class ='header-info'>
        </div>
        <div class ='header-contents'>
        </div>
      </header>
      <div class = 'profile__contents'></div>
      ${modal()}
      <div class = 'alert-modal'>오류 : 닉네임이 중복되었습니다.</div>
    `;
  }

  mounted(): void {
    const headerInfo = this.$target.querySelector(
      ".header-info"
    ) as HTMLElement;

    const header_contents = this.$target.querySelector(
      ".header-contents"
    ) as HTMLElement;

    new HeaderInfo(headerInfo, {
      isMyProfile: this.state.isMyProfile,
      isFollow: this.state.isFollow,
      nickname: this.state.nickname,
      email: this.state.email,
    });
    new HeaderContents(header_contents, {
      posts: this.state.images?.length,
      follower: this.state.follower,
      following: this.state.following,
    });

    const profile_contents = this.$target.querySelector(
      ".profile__contents"
    ) as HTMLElement;

    this.state.profileMode === "posts" &&
      new PostLists(profile_contents, { images: this.state.images });

    this.state.profileMode === "follower" &&
      new Followlists(profile_contents, {
        profileMode: this.state.profileMode,
        isMyProfile: this.state.isMyProfile,
        follows: this.state.followers,
        nickname: this.state.nickname,
      });

    this.state.profileMode === "following" &&
      new Followlists(profile_contents, {
        profileMode: this.state.profileMode,
        isMyProfile: this.state.isMyProfile,
        follows: this.state.followings,
        nickname: this.state.nickname,
      });
  }

  setEvent(): void {
    const modifyInfoButton = this.$target.querySelector(
      ".modify-button"
    ) as HTMLFormElement;
    modifyInfoButton?.addEventListener("click", (e: Event) => {
      e.preventDefault();
      const modal = document.body.querySelector(".alert-modal") as HTMLElement;
      modal.classList.add("blue");
      showErrorModal(modal, "회원정보가 변경되었습니다");
      setTimeout(() => {
        document.body
          .querySelector(".modify-modal")
          ?.classList.toggle("hidden");
      }, 2000);

      /**todo
       * 1. 서버와 통신후 응답받음
       * 2-1. 유저 정보 변경 성공하면 정보 변경 알려주고 모달창 없애기
       * 2-2. 만약 실패하면 경고창 띄우고 모달창 유지
       */
    });

    if (this.$target.classList.contains("once")) return;
    this.$target.classList.add("once");

    this.$target.addEventListener("click", (e: Event) => {
      e.preventDefault();
      const target = e.target as HTMLElement;

      const postsSection = target.closest("section.profile-posts");
      const followerSection = target.closest("section.profile-follower");
      const followingSection = target.closest("section.profile-following");
      const followButton = target.closest(".follow-button");
      const eyes = target.closest(".eyes");

      if (eyes) {
        eyes.classList.toggle("slash");
        const inputBox = eyes.parentElement?.querySelector(
          "input"
        ) as HTMLInputElement;
        eyes.classList.contains("slash")
          ? (inputBox.type = "password")
          : (inputBox.type = "text");
        return;
      }

      if (postsSection) {
        this.setState({ ...this.state, profileMode: "posts" });
        return;
      }

      if (followerSection) {
        this.setState({ ...this.state, profileMode: "follower" });
        return;
      }

      if (followingSection) {
        this.setState({ ...this.state, profileMode: "following" });
        return;
      }

      if (followButton) {
        this.toggleFollow();
      }
    });
  }

  async toggleFollow() {
    this.setState({ ...this.state, isFollow: !this.state.isFollow });
    await basicAPI.post(`/api/profile/follow`, {
      followingNickname: this.state.nickname,
    });
    this.fetchProfilePage(this.state.nickname);
  }
}

function showErrorModal(modal: HTMLElement, errorMessage: string): void {
  if (modal.classList.contains("FadeInAndOut")) return;
  modal.innerHTML = errorMessage;
  modal.classList.toggle("FadeInAndOut");
  setTimeout(() => {
    modal.classList.toggle("FadeInAndOut");
  }, 2000);
}

function modal(): string {
  return /*html*/ `
  <div class ='modify-modal hidden'>
    <div class = 'title'>회원 정보</div>
    <div class ='modify-modal-form'>
      <form>
        <div class ='modify-modal-form-nickname'>
        <h3 class = 'form-label'>닉네임</h3>
        <input class ='input-box' placeholder='현재 닉네임'/>
        <div class ='eyes slash'></div>
        </div>
        <div class ='modify-modal-form-password'>
        <h3 class = 'form-label'>비밀번호</h3>
        <input type ='password' class ='input-box' placeholder='새 비밀번호'/>
        <div class ='eyes slash'></div>
        </div>
        <div class ='modify-modal-form-password-confirm'>
          <h3 class = 'form-label'>비밀번호 확인</h3>
          <input type ='password' class ='input-box' placeholder='기존 비밀번호'/>
          <div class ='eyes slash'></div>
        </div>
        <div class ='modify-modal-footer'><button class ='modify-button'>변경</button> </div>
          
      </form>
    </div>
  </div>
  `;
}
