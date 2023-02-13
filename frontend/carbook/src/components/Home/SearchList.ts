import { categoryMap } from '@/constants/category';
import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import { actionType, tagStore } from '@/store/tagStore';
import { getClosest } from '@/utils';
import { getTagIcon } from './helper';

export default class SearchList extends Component {
  setup(): void {
    this.state = this.props;
  }

  template(): string {
    const { keywords, option } = this.state;

    return `
      ${
        !keywords.length
          ? `<div class="dropdown__msg">검색 결과가 없습니다.</div>`
          : ''
      }
      <div class="dropdown__cards--scroll">
      ${keywords
        .filter(({ category = 'hashtag' }: IHashTag) => category === option)
        .map(
          ({ id, category, tag }: IHashTag) => `
            <div class="dropdown__card" data-id="${id}" data-category="${category}" data-tag="${tag}">
              <div class="dropdown__card--icon">${getTagIcon(category)}</div>
              <div class="dropdown__card--text">${tag}</div>
              <div class="dropdown__card--type ${category}">${
            categoryMap[category]
          }</div>
            </div>
          `
        )
        .join('')}
        </div>
    `;
  }

  setEvent(): void {
    this.$target.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;
      const dropdownCard = getClosest(target, '.dropdown__card');

      if (dropdownCard) {
        const { id, category, tag } = dropdownCard.dataset;
        tagStore.dispach({
          type: actionType.ADD_TAG,
          payload: { id, category, tag },
        });
      }
    });
  }
}
