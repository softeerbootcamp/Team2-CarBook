import { Component } from '@/core';
import likeButton from '@/assets/icons/likeButton.svg';
import notLikeButton from '@/assets/icons/notLikeButton.svg';

export default class Footer extends Component {
  template(): string {
    const { like, likeCount, createDate } = this.props;
    return /*html*/ `
        <div class ='like-info'>
          <div class ='like-button'><img src="${
            like ? likeButton : notLikeButton
          }"/></div>
          <div class ='like-count'>${likeCount ?? ''} likes</div>
        </div>
        <div class ='posted-time'>${createDate ?? ''}</div>
        `;
  }
}
