import { Component } from "@/core";
import "./Profile.scss";

import {
  HeaderInfo,
  HeaderContents,
  PostLists,
  Followlists,
  ModifyModal,
} from "@/components/Profile";

import { basicAPI } from "@/api";

export default class ProfilePage extends Component {
  setup(): void {
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
      <div class ='modify-modal'></div>
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

    const modifyModal = this.$target.querySelector(
      ".modify-modal"
    ) as HTMLElement;
    new ModifyModal(modifyModal);
  }

  // async modifyUserInfo(){
  //   this.setState({ ...this.state, isFollow: !this.state.isFollow });
  //   await basicAPI.post(`/api/profile/follow`, {
  //     followingNickname: this.state.nickname,
  //   });
  //   this.fetchProfilePage(this.state.nickname);
  // }

  setEvent(): void {
    // const modifyInfoButton = this.$target.querySelector(
    //   ".modify-button"
    // ) as HTMLFormElement;
    // modifyInfoButton?.addEventListener("click", (e: Event) => {
    //   console.log("clicked button");
    //   e.preventDefault();
    //   const modal = document.body.querySelector(".alert-modal") as HTMLElement;
    //   modal.classList.add("blue");
    //   showErrorModal(modal, "회원정보가 변경되었습니다");
    //   setTimeout(() => {
    //     document.body
    //       .querySelector(".modify-modal")
    //       ?.classList.toggle("FadeInAndOut");
    //   }, 2000);

    //   /**todo
    //    * 1. 서버와 통신후 응답받음
    //    * 2-1. 유저 정보 변경 성공하면 정보 변경 알려주고 모달창 없애기
    //    * 2-2. 만약 실패하면 경고창 띄우고 모달창 유지
    //    */
    // });

    if (this.$target.classList.contains("once")) return;
    this.$target.classList.add("once");

    this.$target.addEventListener("click", (e: Event) => {
      e.preventDefault();
      const target = e.target as HTMLElement;

      const postsSection = target.closest("section.profile-posts");
      const followerSection = target.closest("section.profile-follower");
      const followingSection = target.closest("section.profile-following");
      const followButton = target.closest(".follow-button");
      const modifyInfoButton = target.closest(".modify-button");

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

      if (modifyInfoButton) {
        console.log("clicked button");
        e.preventDefault();
        const modal = document.body.querySelector(
          ".alert-modal"
        ) as HTMLElement;
        modal.classList.add("blue");
        showErrorModal(modal, "회원정보가 변경되었습니다");
        // setTimeout(() => {
        //   document.body
        //     .querySelector(".modify-modal")
        //     ?.classList.toggle("FadeInAndOut");
        // }, 2000);
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
