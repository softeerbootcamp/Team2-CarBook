import { Component } from '@/core';
import { tagStore } from '@/store/tagStore';

export default class PostList extends Component {
  setup(): void {
    tagStore.subscribe(this, this.render.bind(this));
  }
  template(): string {
    const { postList } = this.props;

    return `
     ${postList
       .map(
         (post: any) => ` <div class="main__gallery--image" data=${post}></div>`
       )
       .join('')}
    `;
  }
}
