import { Component } from "@/core";
import "./Profile.scss";

type NickName = string;

export default class ProfilePage extends Component {
  setup(): void {
    this.setState({
      isMyProfile: false,
      isFollow: false,
      profileMode: "following",
      followers: [
        { nickname: "1번째 팔로워" },
        { nickname: "2번째 팔로워" },
        { nickname: "3번째 팔로워" },
      ],
      followings: [
        { nickname: "1번째 팔로워" },
        { nickname: "2번째 팔로워" },
        { nickname: "3번째 팔로워" },
      ],
    });
  }
  template(): string {
    return /*html*/ `
    <div class = 'profile__container'>
      <header>
        <div class ='header-info'>
          <div class = 'header-info-header'>
            <div class ='user-nickname'>유저 닉네임</div>
            <div class ='modify-status'></div>
          </div>
          <div class ='user-email'>test@gmail.com</div>
        </div>
        <div class ='header-contents'>
          <section class ='profile-posts'>
            <div class ='profile-posts-title'>게시물</div>
            <div class ='profile-posts-count'>13</div>
          </section>
          <section class ='profile-follower'>
            <div class ='profile-follower-title'>팔로워</div>
            <div class ='profile-follower-count'>164</div>
          </section>
          <section class ='profile-following'>
            <div class ='profile-following-title'>팔로잉</div>
            <div class ='profile-following-count'>272</div>
          </section>
        </div>
      </header>

      <div class = 'profile__contents'></div>
      ${modal()}
    </div>
    `;
  }

  mounted(): void {
    const modify_status = this.$target.querySelector(
      ".modify-status"
    ) as HTMLElement;
    const profile_contents = this.$target.querySelector(
      ".profile__contents"
    ) as HTMLElement;

    this.state.isMyProfile
      ? new Menu(modify_status)
      : new FollowButton(modify_status, { isFollow: this.state.isFollow });

    this.state.profileMode === "posts" && new Postlists(profile_contents);

    this.state.profileMode === "follower" &&
      new Followlists(profile_contents, {
        profileMode: this.state.profileMode,
        isMyProfile: this.state.isMyProfile,
        follows: this.state.followers,
      });

    this.state.profileMode === "following" &&
      new Followlists(profile_contents, {
        profileMode: this.state.profileMode,
        isMyProfile: this.state.isMyProfile,
        follows: this.state.followings,
      });
  }

  setEvent(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;

      const postsSection = target.closest("section.profile-posts");
      if (postsSection) {
        this.setState({ ...this.state, profileMode: "posts" });
        return;
      }
      const followerSection = target.closest("section.profile-follower");
      if (followerSection) {
        this.setState({ ...this.state, profileMode: "follower" });
        return;
      }
      const followingSection = target.closest("section.profile-following");
      if (followingSection) {
        this.setState({ ...this.state, profileMode: "following" });
        return;
      }
    });
  }
}

class ProfileHeaderInfo extends Component {
  template(): string {
    return /*html*/ `
    <div class ='header-info'>
    <div class = 'header-info-header'>
      <div class ='user-nickname'>유저 닉네임</div>
      <div class ='modify-status'></div>
    </div>
    <div class ='user-email'>test@gmail.com</div>
  </div>
  <div class ='header-contents'>
    <section class ='profile-posts'>
      <div class ='profile-posts-title'>게시물</div>
      <div class ='profile-posts-count'>13</div>
    </section>
    <section class ='profile-follower'>
      <div class ='profile-follower-title'>팔로워</div>
      <div class ='profile-follower-count'>164</div>
    </section>
    <section class ='profile-following'>
      <div class ='profile-following-title'>팔로잉</div>
      <div class ='profile-following-count'>272</div>
    </section>
  </div>
    `;
  }
}

class FollowButton extends Component {
  template(): string {
    const { isFollow } = this.props;
    return `<button class = 'follow-button'>${
      isFollow ? "언팔로우" : "팔로우"
    }</button>`;
  }
}

class Menu extends Component {
  template(): string {
    return /*html */ `
    <div class ='header-info-menu'>
      <ul class = 'info-menu-items'>
        <h3>설정</h3>
        <li>닉네임 변경</li>
        <li>비밀번호 변경</li>
        <li>로그아웃</li>
      </ul>
    </div>`;
  }
}

class Postlists extends Component {
  template(): string {
    return /*html*/ `
    <h2 class = 'profile__contents-header'>
      내 게시물
    </h2>
    <div class="profile__contents-posts">
      <div class="profile__contents-post"></div>
      <div class="profile__contents-post"></div>
      <div class="profile__contents-post"></div>
      <div class="profile__contents-post"></div>
      <div class="profile__contents-post"></div>
      <div class="profile__contents-post"></div>
    </div>
  `;
  }
}

function FollowlistsItem(isMyProfile: boolean, nickname: string) {
  return /*html*/ `
      <li class = 'profile__contents-follower'>
          <div class ='follower-info'>
            <div class ='follower-info-icon'></div>
            <h3 class ='follower-info-nickname'>${nickname}</h3>
          </div>
          <button class =follower-delete-button ${
            isMyProfile ? "" : "hidden"
          }>삭제</button>
        </li>
      `;
}

class Followlists extends Component {
  template(): string {
    const { profileMode, isMyProfile, follows } = this.props;
    return /*html*/ `
      <h2 class = 'profile__contents-header'>
        ${profileMode === "follower" ? "팔로워" : "팔로잉"}
      </h2>
      <ul class = 'profile__contents-followers'>
        ${follows
          .map(({ nickname }: NickName[]) =>
            FollowlistsItem(isMyProfile, nickname)
          )
          .join("")}
      </ul>
      `;
  }
}

function modal(): string {
  return /*html*/ `
  <div class ='modify-modal'>
    <div class = 'title'>회원 정보</div>
    <div class ='modify-modal-form'>
      <form>
        <div class ='modify-modal-form-nickname'>
        <h3 class = 'form-label'>닉네임</h3>
        <input class ='input-box' placeholder='현재 닉네임'/>
        </div>
        <div class ='modify-modal-form-nickname'>
        <h3 class = 'form-label'>비밀번호</h3>
        <input class ='input-box' placeholder='새 비밀번호'/>
        </div>
        <div class ='modify-modal-form-nickname'>
          <h3 class = 'form-label'>비밀번호 확인</h3>
          <input class ='input-box' placeholder='기존 비밀번호'/>
        </div>
        <div class ='modify-modal-footer'>
          <button class ='modify-button'>변경</button>
        </div>
      </form>
    </div>
  </div>
  `;
}
