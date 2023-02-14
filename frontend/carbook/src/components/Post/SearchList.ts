import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import { getClosest } from '@/utils';
import plus from '@/assets/icons/plus.svg';

export default class SearchList extends Component {
  setup(): void {
    this.state = this.props;
    this.addHashTag();
  }

  template(): string {
    const { hashtags, keyword } = this.state;

    return `
      ${this.getMsg(hashtags, keyword)}
      ${hashtags
        .map(
          ({ tag }: IHashTag) => `
            <div class="dropdown__card" data-tag=${tag}>${tag}</div> `
        )
        .join('')}
    `;
  }

  getMsg(hashtags: Array<IHashTag>, keyword: string) {
    if (hashtags.length === 0) {
      if (keyword.length !== 0) {
        return `<div class="dropdown__msg" data-tag="${keyword}">
          <img src=${plus} alt="plus"/>  
          새로운 <div class="strong">"${keyword}"</div> 추가하기     
        </div>`;
      } else {
        return `<div class="dropdown__msg" data-tag="">
          검색어를 입력해 주세요 
        </div>`;
      }
    }

    return '';
  }

  addHashTag() {
    this.$target.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;
      const card = getClosest(target, '.dropdown__card');
      const msg = getClosest(target, '.dropdown__msg');

      if (card) {
        const { tag } = card.dataset;
        this.props.addHashTag({ [tag]: 'old' });
        return;
      }

      if (msg) {
        const { tag } = msg.dataset;
        if (tag?.length !== 0) {
          this.props.addHashTag({ [tag]: 'new' });
        }
      }
    });
  }
}
