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
import {
  DUPPLICATEDNICKNAME,
  EMPTYMODIFYCONFIRMPW,
  EMPTYMODIFYPW,
  EMPTYNICKNAME,
  EMPTYPW,
  NotMatchedPassword,
} from "@/constants/errorMessage";
import { push, replace } from "@/utils/router/navigate";

export default class ProfilePage extends Component {
  setup(): void {
    const urlnickname = location.pathname.split("/").slice(-1)[0];
    this.state.nickname = urlnickname;
    this.state.profileMode = "posts";
    this.state.isloading = true;

    this.fetchProfilePage(urlnickname);
  }

  async fetchProfilePage(urlnickname: string) {
    const data = await basicAPI
      .get(`/api/profile?nickname=${urlnickname}`)
      .then((response) => response.data)
      .catch((error) => {
        const errorMessage = error.response.data.message;
        if (errorMessage.includes("Session")) {
          this.state.notSession = true;
          push("/login");
          return;
        }
        if (errorMessage.includes("Nickname")) {
          this.state.notExistedNickname = true;
          return;
        }
      });

    this.setState({
      ...this.state,
      isMyProfile: data?.myProfile,
      isFollow: data?.follow,
      nickname: data?.nickname,
      email: data?.email,
      images: data?.images,
      follower: data?.follower,
      following: data?.following,
      isloading: false,
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
      <div class = 'not-exist-nickname'>
        <div class = 'alert-main-image'></div>
        <div class ='alert-contents'>
          <div class ='alert-image'></div>
          <div>
          존재하지 않는 닉네임입니다!<br/>
          해당하는 유저의 정보가 존재하지 않습니다
          </div>
        </div>
      </div>
    `;
  }

  render(): void {
    if (this.state.isloading || this.state?.notSession) return;

    this.$target.innerHTML = this.template();

    if (this.state?.notExistedNickname) {
      const wrongNickNamePage = this.$target.querySelector(
        ".not-exist-nickname"
      );
      wrongNickNamePage?.classList.add("visible");
      return;
    }

    const profileContainer = this.$target.querySelector(
      ".profile__container"
    ) as HTMLElement;

    if (profileContainer.classList.contains("once")) return;
    profileContainer.classList.add("once");

    profileContainer.addEventListener("click", (e: Event) => {
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
        e.preventDefault();
        const modal = this.$target.querySelector(".alert-modal") as HTMLElement;
        const modifyModal = target.closest(".modify-modal") as HTMLElement;
        if (modifyModal.classList.contains("nickname")) {
          this.modifyNickname({
            alertModal: modal,
            modal: modifyModal,
            beforeNickname: this.state.nickname,
          });
          return;
        }
        if (modifyModal.classList.contains("password")) {
          this.modifyPassword({
            alertModal: modal,
            modal: modifyModal,
          });
          return;
        }
        modal.classList.add("blue");
        showErrorModal(modal, "회원정보가 변경되었습니다");
      }
    });

    this.setEvent();
    this.mounted();
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

  async modifyNickname({
    alertModal,
    modal,
    beforeNickname,
  }: {
    alertModal: HTMLElement;
    modal: HTMLElement;
    beforeNickname: string;
  }) {
    const newNicknameInput = modal.querySelector(
      ".modify-modal-form-nickname input"
    ) as HTMLInputElement;
    const newNickname = newNicknameInput.value.trim();

    if (
      IsInvalidNickname({
        alertModal,
        beforeNickname,
        newNickname,
      })
    )
      return;

    await basicAPI
      .patch(`/api/profile/modify/${this.state.nickname}`, {
        newNickname,
      })
      .then(() => {
        this.setState({ ...this.state, nickname: newNickname });
        replace(`/profile/${newNickname}`);
      })
      .catch(() => {
        showErrorModal(alertModal, DUPPLICATEDNICKNAME);
      });
  }

  async modifyPassword({
    alertModal,
    modal,
  }: {
    alertModal: HTMLElement;
    modal: HTMLElement;
  }) {
    const beforePasswordInput = modal.querySelector(
      ".modify-modal-form-password input"
    ) as HTMLInputElement;
    const afterPasswordInput = modal.querySelector(
      ".modify-modal-form-modify-password input"
    ) as HTMLInputElement;
    const afterPasswordConfirmInput = modal.querySelector(
      ".modify-modal-form-password-confirm input"
    ) as HTMLInputElement;

    const beforePassword = beforePasswordInput.value.trim();
    const afterPassword = afterPasswordInput.value.trim();
    const afterPasswordConfirm = afterPasswordConfirmInput.value.trim();

    if (
      IsInvalidPassword({
        beforePassword,
        afterPassword,
        afterPasswordConfirm,
        alertModal,
      })
    )
      return;

    await basicAPI
      .patch(`/api/profile/modify/password`, {
        password: beforePassword,
        newPassword: afterPassword,
      })
      .then((res) => {
        console.log(res.data.message);
        if (res.data.message.includes("ERROR")) {
          showErrorModal(alertModal, "기존 비밀번호가 일치하지 않습니다");
          return;
        }
        showErrorModal(alertModal, "비밀번호 변경에 성공하셨습니다");
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

function IsInvalidNickname({
  alertModal,
  beforeNickname,
  newNickname,
}: {
  alertModal: HTMLElement;
  beforeNickname: string;
  newNickname: string;
}) {
  if (newNickname.length === 0) {
    showErrorModal(alertModal, EMPTYNICKNAME);
    return true;
  }
  if (newNickname === beforeNickname) {
    showErrorModal(alertModal, DUPPLICATEDNICKNAME);
    return true;
  }

  return false;
}

function IsInvalidPassword({
  alertModal,
  beforePassword,
  afterPassword,
  afterPasswordConfirm,
}: {
  alertModal: HTMLElement;
  beforePassword: string;
  afterPassword: string;
  afterPasswordConfirm: string;
}) {
  // 변경할 비밀번호와 비밀번호 확인이 일치하는지 체크
  if (beforePassword.length === 0) {
    showErrorModal(alertModal, EMPTYPW);
    return true;
  }

  if (afterPassword.length === 0) {
    showErrorModal(alertModal, EMPTYMODIFYPW);
    return true;
  }

  if (afterPasswordConfirm.length === 0) {
    showErrorModal(alertModal, EMPTYMODIFYCONFIRMPW);
    return true;
  }

  if (afterPassword !== afterPasswordConfirm) {
    showErrorModal(alertModal, NotMatchedPassword);
    return true;
  }

  return false;
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
