import { Component } from '@/core';

export default class Header extends Component {
  template(): string {
    return `
      <div class="header__left-box">
        <div class="header__logo">CarBook</div>
        <div class="header__sub">차 사진의 모든 것</div>
      </div>
      <div class="header__right-box">
      </div>
    `;
  }
}
