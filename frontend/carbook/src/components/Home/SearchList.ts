import { categoryMap } from '@/constants/category';
import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import { actionType, tagStore } from '@/store/tagStore';
import { getClosest, longKeywordHandler } from '@/utils';
import { getTagIcon } from './helper';
import spinner from '@/assets/images/spinner.png';

export default class SearchList extends Component {
  setup(): void {
    this.state = this.props;
  }

  template(): string {
    const PLUS_NUMBER = 3;
    const { keywords, option } = this.state;

    const filteredKeywords = keywords.filter(({ category }: IHashTag) => {
      if (option === 'all') return true;
      else return category === option;
    });

    return `
      ${this.getMsg(filteredKeywords)}
      <div class="dropdown__cards--scroll">
      ${filteredKeywords
        .map(
          ({ id, category, tag }: IHashTag) => `
            <div class="dropdown__card" data-id="${id}" data-category="${category}" data-tag="${tag}">
              <div class="dropdown__card--icon">${getTagIcon(category)}</div>
              <div class="dropdown__card--text">${longKeywordHandler(
                tag,
                PLUS_NUMBER
              )}</div>
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

  getMsg(filteredKeywords: Array<IHashTag>) {
    const { isLoading } = this.state;

    if (isLoading) {
      return `<div class="spinner__container"><img class="spinner" src="${spinner}" alt="spinner" /> </div>`;
    }
    if (filteredKeywords.length === 0) {
      return `<div class="dropdown__msg">검색 결과가 없습니다.</div>`;
    }
    return '';
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
