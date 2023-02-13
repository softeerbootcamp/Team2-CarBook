import { Component } from '@/core';
import Header from '@/components/common/Header';
import Form from '@/components/Post/Form';
import { qs } from '@/utils';
import './Post.scss';

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

  mounted(): void {
    const header = qs(this.$target, '.header');
    const form = qs(this.$target, '.form-container');

    new Header(header, { text: '새 게시물 등록하기' });
    new Form(form);
  }
}
