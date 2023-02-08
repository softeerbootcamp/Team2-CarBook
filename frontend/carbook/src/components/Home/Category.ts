import { categoryMap } from '@/constants/category';
import { Component } from '@/core';
import { CategoryType } from '@/interfaces';

export default class Category extends Component {
  setup(): void {
    this.state = {
      option: 'all',
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
    const selections = this.$target.querySelector('.selections');
    const selected = this.$target.querySelector('.selected');

    this.$target?.addEventListener('click', (e) => {
      selected?.classList.toggle('hidden');
      selections?.classList.toggle('active');

      const target = e.target as HTMLElement;
      const classname = target.className as CategoryType;

      const koreaWord = categoryMap[classname];
      if (koreaWord) {
        this.setState({ option: classname });
        this.props.setOption(classname);
      }
    });
  }
}
