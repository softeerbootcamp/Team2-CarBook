import { Component } from '@/core';

export default class TextForm extends Component {
  template(): string {
    return `
      <div class="input__text">글 내용</div>
      <textarea class="input__textarea" placeholder="글 내용을 입력해주세요"></textarea>
    `;
  }
}
