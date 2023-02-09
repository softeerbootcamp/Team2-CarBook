import { Component } from '@/core';
import { tagStore } from '@/store';
import { actionType } from '@/store/tagStore';
import { getClosest, getTagIcon } from '@/utils';

export default class HashTagList extends Component {
  setup(): void {
    tagStore.subscribe(this.render.bind(this));
    this.setState(tagStore.getState());
    this.onClickHandler();
  }
  template(): string {
    const tagListObj = tagStore.getState();
    const hashtagList = Object.entries(tagListObj).map(([key, value]) => {
      return {
        id: key,
        ...value,
      };
    });

    return `
      ${hashtagList
        .map(
          (hashtag) =>
            `<div class="hashtag" data-id=${hashtag.id}>${getTagIcon(
              `${hashtag.category}`
            )} ${hashtag.tag}</div>`
        )
        .join('')}
    `;
  }
  onClickHandler() {
    this.$target.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;
      const hashtag = getClosest(target, '.hashtag');

      const id = hashtag.dataset.id as string;

      tagStore.dispach({
        type: actionType.DELETE_TAG,
        tag: { id },
      });
    });
  }
}
