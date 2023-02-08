import { Component } from '@/core';
import { IHashTag } from '@/interfaces';
import darkcar from '@/assets/icons/dark-car.svg';
import whitecar from '@/assets/icons/white-car.svg';

export default class HashTagList extends Component {
  template(): string {
    const { hashtagList } = this.props;

    return `
      ${hashtagList.map((hashtag: IHashTag) => `<div class="hashtag">${this.getTagIcon(`${hashtag.category}`)} ${hashtag.tag}</div>`).join('')}
    `;
  }

  getTagIcon(type: string) {
    switch (type) {
      case 'model':
        return `<img src="${darkcar}" />`;
      case 'type':
        return `<img src="${whitecar}" />`;
      case 'hashtag':
        return '#';
      default:
        return;
    }
  }
}
