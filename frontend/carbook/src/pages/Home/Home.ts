import { Component } from '@/core';
import './Home.scss';

export default class HomePage extends Component {
  template(): string {
    return `
    <div class="home-container">
      <header class="header">
        <div class="header__left-box">
          <div class="header__logo">CarBook</div>
          <div class="header__sub">차 사진의 모든 것</div>
        </div>
        <div class="header__right-box">
        </div>
      </header>
      <section class="section">
        <input class="section__input" type="text" placeholder="키워드를 입력해주세요"/>
        <div class="section__search-img" alt="search-icon"></div>
        <div class="section__dropdown">
          <div class="dropdown__card">
            <div class="dropdown__card--icon"></div>
            <div class="dropdown__card--text">차 종류</div>
            <div class="dropdown__card--type">태그</div>
          </div>
        </div>
      </section>  
      <main class="main">
        <div class="main__title">My Feed</div>
        <div class="main__hashtage">
          <div class="hashtage"># car</div>
          <div class="hashtage"># car</div>
          <div class="hashtage"># car</div>
          <div class="hashtage"># car</div>
          <div class="hashtage"># car</div>
          <div class="hashtage"># car</div>
        </div>
        <div class="main__gallery">
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
        </div>
        <div class="main__button">+</div>
      </main>
    </div>
    `;
  }
}
