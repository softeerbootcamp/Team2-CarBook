import { Component } from '@/core';

export default class Header extends Component {
  template(): string {
    const { text } = this.props;
    return `
      <div class="header__left-box">
        <div class="header__logo">CarBook</div>
        <div class="header__sub">${text}</div>
      </div>
      <div class="header__right-box">
      </div>
    `;
  }
}
