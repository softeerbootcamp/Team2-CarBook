import { Component } from '@/core';
import { tagStore } from '@/store/tagStore';
import { actionType } from '@/store/tagStore';
import { getClosest } from '@/utils';
import { getTagIcon } from './helper';

export default class HashTagList extends Component {
  setup(): void {
    tagStore.subscribe(this, this.render.bind(this));
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
      ${
        hashtagList.length === 0
          ? '<div class="msg">🔍 검색을 통해 원하는 태그를 추가하세요</div>'
          : ''
      }
      ${hashtagList
        .map(
          (hashtag) =>
            `<div class="hashtag" data-id=${hashtag.id} data-tag=${
              hashtag.tag
            }>${getTagIcon(`${hashtag.category}`)} <div>${
              hashtag.tag
            }</div></div>`
        )
        .join('')}
    `;
  }
  onClickHandler() {
    this.$target.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;
      const hashtag = getClosest(target, '.hashtag');

      if (hashtag) {
        const id = hashtag.dataset.id as string;

        tagStore.dispach({
          type: actionType.DELETE_TAG,
          payload: { id },
        });
      }
    });
  }
}
