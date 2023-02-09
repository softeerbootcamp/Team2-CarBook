import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import { getTagIcon } from '@/utils';

export default class HashTagList extends Component {
  template(): string {
    const { hashtagList } = this.props;

    return `
      ${hashtagList.map((hashtag: IHashTag) => `<div class="hashtag">${getTagIcon(`${hashtag.category}`)} ${hashtag.tag}</div>`).join('')}
    `;
  }
}
