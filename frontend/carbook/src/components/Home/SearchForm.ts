import { Component } from '@/core';

export default class SearchForm extends Component {
  template(): string {
    return `
      <input class="section__input" type="text" placeholder="키워드를 입력해주세요"/>
        <div class="section__search-img" alt="search-icon"></div>
        <div class="section__dropdown">
          <div class="dropdown__card">
            <div class="dropdown__card--icon"></div>
            <div class="dropdown__card--text">차 종류</div>
            <div class="dropdown__card--type">태그</div>
          </div>
      </div>
    `;
  }

  setEvent(): void {}
}
