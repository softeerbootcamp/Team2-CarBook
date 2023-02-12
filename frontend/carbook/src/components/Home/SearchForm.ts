import { basicAPI } from '@/api';
import { Component } from '@/core';
import { CategoryType } from '@/interfaces';
import { getClosest, qs } from '@/utils';
import Category from './Category';
import SearchList from './SearchList';

interface ISearchFormData {
  visible?: boolean;
  option?: CategoryType;
  keywords?: [];
}

export default class SearchForm extends Component {
  data: ISearchFormData = {};
  searchList: any;
  setup(): void {
    this.data = {
      option: 'hashtag',
      keywords: [],
    };
  }

  template(): string {
    return `
      <input class="section__input" type="text" placeholder="키워드를 입력해주세요"/>
      <div class="section__search-img" alt="search-icon"></div>
      <div class="section__dropdown">
        <div class="dropdown__category">
        </div>
        <div class="dropdown__cards">
        </div>
      </div>
    `;
  }

  mounted(): void {
    const category = this.$target.querySelector(
      '.dropdown__category'
    ) as HTMLElement;
    const cards = this.$target.querySelector('.dropdown__cards') as HTMLElement;

    new Category(category, {
      setOption: (option: CategoryType) => {
        this.setOption(option);
      },
    });

    const searchList = new SearchList(cards, { keywords: this.data.keywords });
    this.searchList = searchList;
  }

  setEvent(): void {
    const input = qs(this.$target, '.section__input') as HTMLInputElement;
    this.onChangeInputHandler(input);

    const container = qs(document, '.home-container');
    container.addEventListener('click', ({ target }) => {
      const dropdown = getClosest(<HTMLElement>target, '.section__dropdown');
      const selections = getClosest(<HTMLElement>target, '.selections');

      if (dropdown || selections) return;

      this.onVisibleHandler(input);
    });
  }

  onChangeInputHandler(input: HTMLInputElement) {
    let timer: ReturnType<typeof setTimeout>;
    input?.addEventListener('keyup', () => {
      const { value } = input;

      if (timer) clearTimeout(timer);
      timer = setTimeout(() => {
        this.getSearchedData(value, this.data.option);
      }, 200);
    });
  }

  onVisibleHandler(input: HTMLInputElement) {
    const isActive = document.activeElement;
    const dropdown = this.$target.querySelector('.section__dropdown');

    if (isActive !== input) {
      dropdown?.classList.remove('active');
    } else {
      dropdown?.classList.add('active');
    }
  }

  setOption(option: CategoryType) {
    this.data.option = option;
  }

  async getSearchedData(keyword: string, category = 'hashtag') {
    const searchedData = await basicAPI.get(
      `/api/search/${category}/?keyword=${keyword.trim()}`
    );
    this.data = { ...this.data, keywords: searchedData.data.hashtags };
    this.searchList.setState({ keywords: searchedData.data.hashtags });
  }
}
