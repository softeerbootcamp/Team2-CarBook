import { Component } from '@/core';
import FollowButton from './FollowButton';
import Menu from './Menu';
import { getClosest } from '@/utils';
import { push } from '@/utils/router/navigate';

export default class HeaderInfo extends Component {
  template(): string {
    const { nickname, email } = this.props;
    return /*html*/ `
      <div class = 'header-info-header'>
        <div class ='user-nickname'>
        <div class = 'home'></div>
          ${nickname ?? ''}
        </div>
        <div class ='modify-status'></div>
      </div>
      <div class ='user-email'>${email ?? ''}</div>
      `;
  }

  mounted(): void {
    const { isMyProfile, isFollow, nickname } = this.props;
    const modify_status = this.$target.querySelector(
      '.modify-status'
    ) as HTMLElement;

    if (isMyProfile === undefined) return;
    isMyProfile
      ? new Menu(modify_status, { nickname })
      : new FollowButton(modify_status, { isFollow });
  }

  setEvent(): void {
    const { nickname } = this.props;
    const headerInfo = this.$target.querySelector(
      '.header-info-header'
    ) as HTMLElement;
    headerInfo?.addEventListener('click', (e: Event) => {
      const target = e.target as HTMLElement;
      const home = getClosest(target, '.home');
      const nicknameDiv = getClosest(target, '.user-nickname');
      if (home) {
        push('/');
        return;
      }
      if (nicknameDiv) push(`/profile/${nickname}`);
    });
  }
}
