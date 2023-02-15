import { basicAPI } from "@/api";
import { Component } from "@/core";
import { push } from "@/utils/router/navigate";

export default class Followlists extends Component {
  setup(): void {
    this.state.follows = [];
    this.receiveFollowLists();
    this.$target.addEventListener("click", (e: Event) => {
      const deleteButton = (e.target as HTMLElement).closest(
        ".follower-delete-button"
      );
      // //팔로워 취소버튼 눌렀을때
      if (deleteButton) {
        const nickname = (
          this.$target.querySelector(".follower-info") as HTMLElement
        ).dataset.nickname as string;
        const mode = this.$target.querySelector(
          ".profile__contents-header"
        )?.innerHTML;
        console.log(mode, typeof mode);

        mode === "팔로워"
          ? this.deleteFollower(nickname)
          : this.deleteFollowing(nickname);
        return;
      }

      const target = (e.target as HTMLElement).closest(
        ".follower-info"
      ) as HTMLElement;

      if (target.dataset.nickname) push(`/profile/${target.dataset.nickname}`);
    });
  }

  /**
   * param: nickname()
   * 서버에 팔로워 삭제, 언팔로우 요청
   *
   */
  async deleteFollower(nickname: string) {
    await basicAPI.delete(`/api/profile/follower?follower=${nickname}`);
    this.receiveFollowLists();
  }

  async deleteFollowing(nickname: string) {
    await basicAPI.post("/api/profile/follow", { followingNickname: nickname });
    this.receiveFollowLists();
  }

  async receiveFollowLists() {
    const { profileMode, nickname } = this.props;

    const mode = profileMode === "follower" ? "followers" : "followings";
    const data = await basicAPI
      .get(`/api/profile/${mode}?nickname=${nickname}`)
      .then((response) => response.data)
      .catch((error) => error);

    this.setState({
      ...this.state,
      follows: data.nicknames,
    });
  }

  template(): string {
    const { profileMode, isMyProfile } = this.props;
    return /*html*/ `
        <h2 class = 'profile__contents-header'>${
          profileMode === "follower" ? "팔로워" : "팔로잉"
        }</h2>
        <ul class = 'profile__contents-followers'>
        ${this.state.follows
          .map((nickname: string) => FollowlistsItem(isMyProfile, nickname))
          .join("")}
        </ul>
        `;
  }

  setEvent(): void {}
}

function FollowlistsItem(isMyProfile: boolean, nickname: string) {
  return /*html*/ `
        <li class = 'profile__contents-follower'>
            <div class ='follower-info' data-nickname= "${nickname}">
              <div class ='follower-info-icon'></div>
              <h3 class ='follower-info-nickname'>${nickname}</h3>
            </div>
            <button class =follower-delete-button ${
              isMyProfile ? "" : "hidden"
            }>삭제</button>
          </li>
        `;
}
