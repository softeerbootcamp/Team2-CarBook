import { Component } from "@/core";

export default class InfoContents extends Component {
  template(): string {
    const { type, model, content } = this.props;
    return /*html*/ `
      <div class ='info-tag-cards'>
      <div class ='info-type-card'>${type}</div>
      <div class ='info-model-card'>${model}</div>
    </div>
    <div class ='info-hashtags'>
      <div class ='info-hashtag'>#rolem</div>
      <div class ='info-hashtag'>#rolem</div>
      <div class ='info-hashtag'>#rolem</div>
    </div>
    <p class = 'info-description'>${content}</p>
      `;
  }
}
