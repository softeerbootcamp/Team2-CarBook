import { Component } from "@/core";
import { IFollows } from "@/interfaces";
import { push } from "@/utils/router/navigate";

export default class Followlists extends Component {
  template(): string {
    const { profileMode, isMyProfile, follows } = this.props;
    return /*html*/ `
        <h2 class = 'profile__contents-header'>
          ${profileMode === "follower" ? "팔로워" : "팔로잉"}
        </h2>
        <ul class = 'profile__contents-followers'>
          ${follows
            .map(({ nickname }: IFollows) =>
              FollowlistsItem(isMyProfile, nickname)
            )
            .join("")}
        </ul>
        `;
  }

  setEvent(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = (e.target as HTMLElement).closest(
        ".follower-info"
      ) as HTMLElement;
      console.log(target.dataset, "target", target);
      if (!target.dataset.nickname) return;
      push("/profile/1");
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
