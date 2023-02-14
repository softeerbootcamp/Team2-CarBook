import { Component } from '@/core';
import { qs } from '@/utils';

export default class TextForm extends Component {
  setup(): void {
    this.state = {
      content: '',
    };
  }
  template(): string {
    return `
      <div class="input__text">글 내용</div>
      <textarea class="input__textarea" placeholder="글 내용을 입력해주세요"></textarea>
    `;
  }

  setEvent(): void {
    this.onChangeHandler();
  }

  onChangeHandler() {
    const textarea = qs(
      this.$target,
      '.input__textarea'
    ) as HTMLTextAreaElement;

    textarea.addEventListener('keyup', () => {
      this.state.content = textarea.value;
    });
  }
}
