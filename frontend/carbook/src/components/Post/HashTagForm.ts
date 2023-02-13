import { Component } from '@/core';
import search from '@/assets/icons/search-blue.svg';

export default class HashTagForm extends Component {
  template(): string {
    return `
      <div class="input__text">해시태그</div>
      <div class="input__box--hashtag">
        <div class="input__wrapper">
          <input class="input" type="text" placeholder="해시태그를 검색해주세요"/>
          <img src="${search}" alt="search-icon" class="icon"/>
        </div>
        <div class="hashtag__box">
          <div class="hashtag"># car</div>
          <div class="hashtag"># car</div>
          <div class="hashtag"># car</div>
        </div> 
      </div>
    `;
  }
}
