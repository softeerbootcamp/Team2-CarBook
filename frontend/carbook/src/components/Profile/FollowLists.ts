import { Component } from '@/core';
import { getClosest } from '@/utils';
import { push } from '@/utils/router/navigate';

export default class Followlists extends Component {
  setEvent(): void {
    const followers = this.$target.querySelector(
      '.profile__contents-followers'
    ) as HTMLElement;
    followers.addEventListener('click', (e: Event) => {
      const target = e.target as HTMLElement;
      const follower = getClosest(target, '.follower-info');
      if (!follower) return;
      const nickname = follower.dataset.nickname;
      push(`/profile/${nickname}`);
    });
  }
  template(): string {
    const { profileMode, isMyProfile, follows } = this.props;
    return /*html*/ `
        <h2 class = 'profile__contents-header'>${
          profileMode === 'follower' ? '팔로워' : '팔로잉'
        }</h2>
        <ul class = 'profile__contents-followers'>
        ${follows
          ?.map((nickname: string) => FollowlistsItem(isMyProfile, nickname))
          .join('')}
        </ul>
        `;
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
              isMyProfile ? '' : 'hidden'
            }>삭제</button>
          </li>
        `;
}
