import './Home.scss';
import { Component } from '@/core';
import Header from '@/components/common/Header';
import { SearchForm, HashTagList, PostList } from '@/components/Home';
import { push } from '@/utils/router/navigate';

export default class HomePage extends Component {
  setup(): void {
    this.setState({
      // 더미 데이터
      isLogin: true,
      nickname: '카북닉네임',
      images: [
        { postid: 1, imageUrl: '스트링' },
        { postid: 2, imageUrl: '2번째 이미지' },
        { postid: 3, imageUrl: '3번째 이미지' },
      ],
    });
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
    const postList = this.$target.querySelector(
      '.main__gallery'
    ) as HTMLElement;

    new Header(header);
    new SearchForm(section);
    new HashTagList(hastagList);
    new PostList(postList, { postList: this.state.images });
  }

  setEvent(): void {
    this.$target.addEventListener('click', (e: Event) => {
      const target = e.target as HTMLElement;

      const profileBtn = target.closest('.header__right-box');
      if (profileBtn) push(`/profile/${this.state.nickname}`);
    });
  }
}
