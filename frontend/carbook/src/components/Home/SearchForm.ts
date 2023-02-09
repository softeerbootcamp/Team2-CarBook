import { basicAPI } from '@/api';
import { categoryMap } from '@/constants/category';
import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import { getTagIcon } from '@/utils';
import Category from './Category';

export default class SearchForm extends Component {
  setup(): void {
    this.state = {
      option: 'all',
      keywords: [
        {
          id: '1',
          category: 'hashtag',
          tag: '이름',
        },
        {
          id: '2',
          category: 'type',
          tag: '이름',
        },
        {
          id: '3',
          category: 'model',
          tag: '이름',
        },
      ],
    };
  }

  template(): string {
    const { keywords } = this.state;

    return `
      <input class="section__input" type="text" placeholder="키워드를 입력해주세요"/>
      <div class="section__search-img" alt="search-icon"></div>
      <div class="section__dropdown">
        <div class="dropdown__category">
        </div>
        ${keywords
          .map(
            ({ id, category, tag }: IHashTag) => `
          <div class="dropdown__card" data-id="${id}">
            <div class="dropdown__card--icon">${getTagIcon(category)}</div>
            <div class="dropdown__card--text">${tag}</div>
            <div class="dropdown__card--type">${categoryMap[category]}</div>
          </div>
        `
          )
          .join('')}
      </div>
    `;
  }

  mounted(): void {
    const category = this.$target.querySelector(
      '.dropdown__category'
    ) as HTMLElement;
    new Category(category, {
      setOption: (option: string) => {
        this.setOption(option);
      },
    });
  }

  setEvent(): void {
    const input = this.$target.querySelector(
      '.section__input'
    ) as HTMLInputElement;

    // 태그 검색
    let timer: ReturnType<typeof setTimeout>;
    input?.addEventListener('keyup', () => {
      const { value } = input;

      if (timer) clearTimeout(timer);
      timer = setTimeout(() => {
        console.log('검색 실행', value, this.state.option);
        //this.searchKeyword(value, this.state.option)
      }, 200);
    });

    const container = document.querySelector('.home-container') as HTMLElement;
    container.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;

      if (target.closest('.section__dropdown') || target.closest('.selections'))
        return;
      this.elementVisibleHandler(input);
    });
  }

  async searchKeyword(keyword: string, category = '') {
    const searchedData = await basicAPI.get(
      `/search/${category}/?keyword=${keyword}`
    );
    this.setState(searchedData.data());
  }

  elementVisibleHandler(input: HTMLInputElement) {
    const isActive = document.activeElement;
    const dropdown = this.$target.querySelector('.section__dropdown');

    if (isActive !== input) {
      dropdown?.classList.remove('active');
    } else {
      dropdown?.classList.add('active');
    }
  }

  setOption(option: string) {
    this.state.option = option;
  }
}
