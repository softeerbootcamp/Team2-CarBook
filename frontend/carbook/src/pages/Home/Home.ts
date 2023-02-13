import './Home.scss';
import { Component } from '@/core';
import Header from '@/components/common/Header';
import { SearchForm, HashTagList, PostList } from '@/components/Home';
import { push } from '@/utils/router/navigate';

export default class HomePage extends Component {
  setup(): void {
    this.state = {
      nickname: 'nickname',
    };
  }

  template(): string {
    return `
    <div class="home-container">
      <header class="header">
      </header>
      <section class="section">
      </section>  
      <main class="main">
        <div class="main__title">My Feed</div>
        <div class="main__hashtags">
        </div>
        <div class="main__gallery">
          <div class="gallery"></div>
          <div class="spinner"></div>
        </div>
        <div class="main__button">+</div>
      </main>
    </div>
    `;
  }

  mounted(): void {
    const header = this.$target.querySelector('.header') as HTMLElement;
    const section = this.$target.querySelector('.section') as HTMLElement;
    const hastagList = this.$target.querySelector(
      '.main__hashtags'
    ) as HTMLElement;
    const postList = this.$target.querySelector('.gallery') as HTMLElement;

    new Header(header);
    new SearchForm(section);
    new HashTagList(hastagList);
    new PostList(postList, {
      setUserInfo: (isLogin: boolean, nickname: string) => {
        this.setUserInfo(isLogin, nickname);
      },
    });
  }

  setEvent(): void {
    this.$target.addEventListener('click', (e: Event) => {
      const target = e.target as HTMLElement;
      const { isLogin, nickname } = this.state;

      const profileBtn = target.closest('.header__right-box');
      const plusBtn = target.closest('.main__button');

      if (profileBtn && isLogin) {
        push(`/profile/${nickname}`);
      } else if (plusBtn && isLogin) {
        push(`/post/new`);
      } else if (profileBtn || plusBtn) {
        push('/login');
      }
    });
  }

  setUserInfo(isLogin: boolean, nickname: string) {
    this.state.nickname = nickname;
    this.state.isLogin = isLogin;
  }
}
