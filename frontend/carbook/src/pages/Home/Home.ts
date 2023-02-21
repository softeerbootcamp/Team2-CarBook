import './Home.scss';
import { Component } from '@/core';
import Header from '@/components/common/Header';
import { SearchForm, HashTagList, PostList } from '@/components/Home';
import { push } from '@/utils/router/navigate';
import isLogin from '@/utils/isLogin';
import plus from '@/assets/icons/plus-white.svg';
import up from '@/assets/icons/up.svg';
import { qs } from '@/utils';

export default class HomePage extends Component {
  async setup() {
    this.state = {
      isLogin: await isLogin(),
      nickname: localStorage.getItem('nickname'),
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
        <div class="main__buttons">
          <div class="main__button up"><img src="${up}" /></div>
          <div class="main__button plus"><img src="${plus}"/></div>
        </div>
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

    new Header(header, { text: '차 사진의 모든 것' });
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
      this.onClickHandler(e);
    });
  }

  onClickHandler(e: Event) {
    const target = e.target as HTMLElement;
    const { isLogin, nickname } = this.state;

    const profileBtn = target.closest('.header__right-box');
    const plusBtn = target.closest('.plus');
    const upBtn = target.closest('.up');

    if (isLogin) {
      if (profileBtn && nickname) {
        push(`/profile/${nickname}`);
        return;
      } else if (plusBtn) {
        push(`/post/new`);
        return;
      }
    } else {
      if (profileBtn || plusBtn) {
        push('/login');
        return;
      }
    }

    if (upBtn) {
      const gallery = qs(this.$target, '.gallery');
      gallery.scrollIntoView({ behavior: 'smooth' });
    }
  }

  setUserInfo(isLogin: boolean, nickname: string) {
    this.state.nickname = nickname;
    this.state.isLogin = isLogin;
  }
}
