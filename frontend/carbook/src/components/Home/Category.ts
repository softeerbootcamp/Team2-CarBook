import { categoryMap } from '@/constants/category';
import { Component } from '@/core';
import { CategoryType } from '@/interfaces';
import { qs } from '@/utils';

export default class Category extends Component {
  setup(): void {
    this.state = {
      option: 'hashtag',
    };
  }

  template(): string {
    const { option } = this.state;

    return `
      검색 조건:<div class="selected">${
        categoryMap[option as CategoryType]
      }</div>
      <div class="selections">
        <div class="all">전체</div>
        <div class="model">모델</div>
        <div class="type">종류</div>
        <div class="hashtag">태그</div>
      </div>
    `;
  }

  setEvent(): void {
    this.onHandleSearchCategory();
  }

  onHandleSearchCategory() {
    const selections = qs(this.$target, '.selections');
    const selected = qs(this.$target, '.selected');

    selected?.addEventListener('click', () => {
      this.onToggleClassName(selected, selections);
    });

    selections?.addEventListener('click', ({ target }) => {
      const classname = (<HTMLElement>target).className as CategoryType;

      const koreaWord = categoryMap[classname];
      if (koreaWord) {
        this.setState({ option: classname });
        this.props.setOption(classname);

        this.onToggleClassName(selected, selections);
      }
    });
  }

  onToggleClassName(selected: HTMLElement, selections: HTMLElement) {
    selected?.classList.toggle('hidden');
    selections?.classList.toggle('active');
  }
}
