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
import { replace } from "@/utils/router/navigate";

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
  setEvent(): void {
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
        e.preventDefault();
        const modal = this.$target.querySelector(".alert-modal") as HTMLElement;
        const modifyModal = target.closest(".modify-modal") as HTMLElement;

        /**
         * 닉네임 수정
         * 모달창의 닉네임 창에 기존 닉네임 입력 o
         * 입력 변경버튼 누르면
         * 1. 빈값인지 체크 o
         * 2. 원래 닉네임이랑 같은지 체크 o
         * 3. 닉네임 변경 신청
         * 4. 다른 유저의 닉네임과 중복 체크
         * 4. 중복됐다면 알려줌
         */
        if (modifyModal.classList.contains("nickname")) {
          this.modifyNickname({
            alertModal: modal,
            modal: modifyModal,
            beforeNickname: this.state.nickname,
          });

          return;
        }

        /**
         * 비밀번호 수정
         * 기존 비밀번호
         * 입력 변경버튼 누르면 전송
         * 1. 기존 비밀번호와 같은지 체크
         * 2. 기존 비밀번호가 맞는지 체크
         * 3. 변경 성공하면 모달창 닫음
         */
        if (modifyModal.classList.contains("password")) {
          if (
            checkInvalidPassword({
              alertModal: modal,
              modal: modifyModal,
            })
          )
            return;

          return;
        }

        //   /**todo
        //    * 1. 서버와 통신후 응답받음
        //    * 2-1. 유저 정보 변경 성공하면 정보 변경 알려주고 모달창 없애기
        //    * 2-2. 만약 실패하면 경고창 띄우고 모달창 유지
        //    */
        // });

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

function checkInvalidPassword({
  alertModal,
  modal,
}: {
  alertModal: HTMLElement;
  modal: HTMLElement;
}) {
  // 변경할 비밀번호와 비밀번호 확인이 일치하는지 체크
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
