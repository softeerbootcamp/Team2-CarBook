import { Component } from '@/core';

export default class InfoContents extends Component {
  template(): string {
    const { type, model, content, hashtags } = this.props;
    return /*html*/ `
      <div class ='info-tag-cards'>
      <div class ='info-type-card' data-tag ='${type}'>${type ?? ''}</div>
      <div class ='info-model-card' data-tag ='${model}'>${model ?? ''}</div>
    </div>
    <div class ='info-hashtags'>
      ${
        hashtags
          ?.map(
            (hashtag: string) =>
              `<div class ='info-hashtag' data-tag ='${hashtag}'>#${hashtag}</div>`
          )
          .join('') ?? ''
      }
    </div>
    <p class = 'info-description'>${content ?? ''}</p>
      `;
  }
}
