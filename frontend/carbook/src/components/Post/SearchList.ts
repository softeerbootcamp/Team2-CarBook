import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import { getClosest, longKeywordHandler } from '@/utils';
import plus from '@/assets/icons/plus.svg';
import spinner from '@/assets/images/spinner.png';

export default class SearchList extends Component {
  setup(): void {
    this.state = this.props;
    this.addHashTag();
  }

  template(): string {
    const { hashtags } = this.state;

    return `
      ${this.getMsg()}
      ${hashtags
        .map(
          ({ tag }: IHashTag) => `
            <div class="dropdown__card" data-tag=${tag}>${tag}</div> `
        )
        .join('')}
    `;
  }

  getMsg() {
    const { isLoading, hashtags, keyword } = this.state;

    if (isLoading)
      return `<div class="spinner__container"><img class="spinner" src="${spinner}" alt="spinner" /> </div>`;

    if (hashtags.length === 0) {
      if (keyword.length !== 0) {
        return `<div class="dropdown__msg" data-tag="${keyword}">
          <img src=${plus} alt="plus"/>  
          새로운 <div class="strong">"${longKeywordHandler(
            keyword
          )}"</div> 추가하기     
        </div>`;
      }
    }
    return ``;
  }

  addHashTag() {
    this.$target.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;
      const card = getClosest(target, '.dropdown__card');
      const msg = getClosest(target, '.dropdown__msg');

      if (card) {
        const tag = card.dataset.tag as string;
        this.props.addHashTag({ [tag]: 'old' });
        return;
      }

      if (msg) {
        const tag = msg.dataset.tag as string;
        if (tag?.length !== 0) {
          this.props.addHashTag({ [tag]: 'new' });
        }
      }
    });
  }
}
