import { basicAPI } from "@/api";
import { Component } from "@/core";
// import { IFollows } from "@/interfaces";
import { push } from "@/utils/router/navigate";

export default class Followlists extends Component {
  setup(): void {
    this.setState({ follows: [] });
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
        <h2 class = 'profile__contents-header'>
          ${profileMode === "follower" ? "팔로워" : "팔로잉"}
        </h2>
        <ul class = 'profile__contents-followers'>
        ${this.state.follows
          .map((nickname: string) => FollowlistsItem(isMyProfile, nickname))
          .join("")}
        </ul>
        `;
  }

  setEvent(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = (e.target as HTMLElement).closest(
        ".follower-info"
      ) as HTMLElement;

      //팔로워 취소버튼 눌렀을때
      // if (!target.closest)

      if (!target.dataset.nickname) return;
      push(`/profile/${target.dataset.nickname}`);
    });
  }
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
