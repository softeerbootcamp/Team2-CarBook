import { basicAPI } from '@/api';
import { Component } from '@/core';
import { CategoryType } from '@/interfaces';
import {
  getClosest,
  onChangeInputHandler,
  onVisibleHandler,
  qs,
} from '@/utils';
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
      option: 'all',
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

    const searchList = new SearchList(cards, {
      isLoading: false,
      keywords: this.data.keywords,
      option: this.data.option,
    });
    this.searchList = searchList;
  }

  setEvent(): void {
    const input = qs(this.$target, '.section__input') as HTMLInputElement;
    onChangeInputHandler(input, this.getSearchedData.bind(this));

    const container = qs(document, '.home-container');
    container.addEventListener('click', ({ target }) => {
      const dropdown = getClosest(<HTMLElement>target, '.section__dropdown');
      const selections = getClosest(<HTMLElement>target, '.selections');

      if (dropdown || selections) return;
      onVisibleHandler(input, '.section__dropdown');
    });
  }

  setOption(option: CategoryType) {
    this.data.option = option;
    this.searchList.setState({ option: this.data.option });
  }

  async getSearchedData(keyword: string) {
    this.searchList.setState({
      keywords: [],
      isLoading: true,
    });

    const trimKeyword = keyword.trim();
    if (trimKeyword.length === 0) {
      this.searchList.setState({
        keywords: [],
        isLoading: false,
      });

      return;
    }

    const searchedData = await basicAPI.get(
      `/api/search/?keyword=${trimKeyword.trim()}`
    );

    this.data = { ...this.data, keywords: searchedData.data.keywords };
    this.searchList.setState({
      keywords: searchedData.data.keywords,
      isLoading: false,
    });
  }
}
