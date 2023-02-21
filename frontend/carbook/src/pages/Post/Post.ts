import { Component } from '@/core';
import Header from '@/components/common/Header';
import Form from '@/components/Post/Form';
import { getClosest, qs } from '@/utils';
import './Post.scss';
import { basicAPI } from '@/api';
import { push } from '@/utils/router/navigate';

export default class PostPage extends Component {
  template(): string {
    return `
    <div class="post-container">
      <header class="header">
      </header>
      <section></section>
      <main class="main">
        <div class="form-container">
        </div>
      </main>
    </div>
    `;
  }

  async mounted() {
    const header = qs(this.$target, '.header');
    const form = qs(this.$target, '.form-container');

    new Header(header, { text: '새 게시물 등록하기' });
    new Form(form, {
      prevPost: await this.getPrevPost(),
      postId: this.getPostId(),
    });
  }

  async getPrevPost() {
    const postId = this.getPostId();

    if (postId) {
      const prevData = await basicAPI.get(`/api/post?postId=${postId}`);
      return prevData.data;
    }

    return;
  }

  setEvent(): void {
    this.$target.addEventListener('click', ({ target }) => {
      const logo = getClosest(<HTMLElement>target, '.header__left-box');
      if (logo) push('/');
    });
  }

  getPostId() {
    return parseInt(location.pathname.split('/')[2]);
  }
}
