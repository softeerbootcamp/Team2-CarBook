import { Component } from '@/core';
import search from '@/assets/icons/search-blue.svg';
import { onChangeInputHandler, onVisibleHandler, qs } from '@/utils';
import { basicAPI } from '@/api';
import SearchList from './SearchList';
import { IHashTag } from '@/interfaces';

export default class HashTagForm extends Component {
  searchList: any;
  setup(): void {
    this.state = {
      value: '',
      hashtags: [],
    };
  }
  template(): string {
    const { value, hashtags } = this.state;

    return `
      <div class="input__text">해시태그</div>
      <div class="input__box--hashtag">
        <div class="input__wrapper">
          <input class="input" value="${value}" type="text" placeholder="해시태그를 검색해주세요"/>
          <img src="${search}" alt="search-icon" class="icon"/>
        </div>
        <div class="dropdown">
        </div>
        <div class="hashtag__box">
          ${this.makeHashtagCards(hashtags)}
        </div> 
      </div>
    `;
  }

  mounted(): void {
    const dropdown = qs(this.$target, '.dropdown');
    const searchList = new SearchList(dropdown, {
      hashtags: [],
      keyword: '',
      addHashTag: (hashtag: IHashTag) => {
        this.addHashTag(hashtag);
      },
    });
    this.searchList = searchList;
  }

  setEvent(): void {
    const form = qs(document, '.form');
    const input = qs(this.$target, '.input') as HTMLInputElement;

    form.addEventListener('click', ({ target }) => {
      const dropdown = (<HTMLElement>target).closest('.dropdown');

      if (dropdown) return;
      onVisibleHandler(input, '.dropdown');
    });

    onChangeInputHandler(input, this.getSearchTags.bind(this));
  }

  makeHashtagCards(hashtags: []) {
    if (hashtags.length === 0) {
      return '<div class="msg">🔍 검색을 통해 원하는 해시태그를 추가하세요</div>';
    } else {
      return `
        ${hashtags
          .map(
            ({ tag, type }: { tag: string; type: string }) =>
              `<div class="hashtag ${type}"># ${tag}</div> `
          )
          .join('')}`;
    }
  }

  addHashTag(hashtag: IHashTag) {
    this.setState({ hashtags: [...this.state.hashtags, hashtag] });
  }

  async getSearchTags(keyword: string) {
    const searchKeyword = keyword.trim();

    if (searchKeyword.length !== 0) {
      const searchedData = await basicAPI.get(
        `/api/search/hashtag/?keyword=${keyword.trim()}`
      );

      this.searchList.setState({
        hashtags: searchedData.data.hashtags,
        keyword: searchKeyword,
      });
    } else {
      this.searchList.setState({ hashtags: [], keyword: searchKeyword });
    }
  }
}
