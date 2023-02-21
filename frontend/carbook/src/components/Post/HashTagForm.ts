import { Component } from '@/core';
import search from '@/assets/icons/search-blue.svg';
import {
  getClosest,
  getObjectKeyArray,
  isEmptyObj,
  onChangeInputHandler,
  onVisibleHandler,
  qs,
} from '@/utils';
import { basicAPI } from '@/api';
import SearchList from './SearchList';
import { IHashTag } from '@/interfaces';

export default class HashTagForm extends Component {
  searchList: any;
  setup(): void {
    const { hashtags } = this.props;
    this.state = {
      value: '',
      hashtags: hashtags,
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
    const input = qs(this.$target, '.input') as HTMLInputElement;
    const dropdown = qs(this.$target, '.dropdown');
    const searchList = new SearchList(dropdown, {
      isLoading: false,
      hashtags: [],
      keyword: '',
      addHashTag: (hashtag: IHashTag) => {
        this.addHashTag(hashtag);
      },
    });
    this.searchList = searchList;
    onChangeInputHandler(input, this.getSearchTags.bind(this));
  }

  setEvent(): void {
    const form = qs(document, '.form');
    form.addEventListener('click', () => {
      const input = qs(this.$target, '.input') as HTMLInputElement;
      onVisibleHandler(input, '.dropdown');
    });

    this.$target.addEventListener('click', ({ target }) => {
      const dropdown = getClosest(<HTMLElement>target, '.dropdown');
      const form = getClosest(<HTMLElement>target, '.form');
      const hashtag = getClosest(<HTMLElement>target, '.hashtag');

      if (dropdown) return;

      if (form) {
        const input = qs(this.$target, '.input') as HTMLInputElement;
        onVisibleHandler(input, '.dropdown');
        this.getSearchTags(input.value);
        return;
      }
      if (hashtag) {
        const hashtagName = hashtag.dataset.tag as string;
        this.removeHashTag(hashtagName);
        return;
      }
    });
  }

  makeHashtagCards(hashtags: object) {
    if (isEmptyObj(hashtags)) {
      return '<div class="msg">🔍 검색을 통해 원하는 해시태그를 추가하세요</div>';
    } else {
      return `
        ${Object.entries(hashtags)
          .map(
            ([key, value]) => `
           <div class="hashtag ${value}" data-tag="${key}"># ${key}</div>`
          )
          .join('')}
      `;
    }
  }

  addHashTag(hashtag: IHashTag) {
    const { hashtags } = this.state;

    if (Object.keys(hashtags).length >= 3) {
      alert('해시태그는 3개를 초과할 수 없습니다!');
      return;
    }

    const input = qs(this.$target, '.input') as HTMLInputElement;

    this.setState({
      hashtags: { ...hashtags, ...hashtag },
      value: input.value,
    });

    const hashTagsName = getObjectKeyArray(this.state.hashtags);
    this.props.setFormData(hashTagsName);
  }

  removeHashTag(hashtagName: string) {
    const input = qs(this.$target, '.input') as HTMLInputElement;
    delete this.state.hashtags[hashtagName];

    this.setState({
      hashtags: { ...this.state.hashtags },
      value: input.value,
    });

    const hashTagsName = getObjectKeyArray(this.state.hashtags);
    this.props.setFormData(hashTagsName);
  }

  async getSearchTags(keyword: string) {
    const searchKeyword = keyword.trim();

    this.searchList.setState({
      isLoading: true,
      hashtags: [],
      keyword: searchKeyword,
    });

    if (searchKeyword.length !== 0) {
      const searchedData = await basicAPI.get(
        `/api/search/hashtag/?keyword=${encodeURI(searchKeyword)}`
      );

      this.searchList.setState({
        isLoading: false,
        hashtags: searchedData.data.hashtags,
        keyword: searchKeyword,
      });
    } else {
      this.searchList.setState({
        isLoading: false,
        hashtags: [],
        keyword: searchKeyword,
      });
    }
  }
}
