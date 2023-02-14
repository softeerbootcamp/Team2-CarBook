import { Component } from '@/core';
import { qs } from '@/utils';
export default class TextForm extends Component {
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
    this.onChangeInputHandler(textarea, this.props.setFormData);
  }
  onChangeInputHandler(
    input: HTMLInputElement | HTMLTextAreaElement,
    callback: (value: string) => void
  ) {
    let timer: ReturnType<typeof setTimeout>;
    input?.addEventListener('keyup', () => {
      const { value } = input;
      if (timer) clearTimeout(timer);
      timer = setTimeout(() => {
        callback && callback(value);
      }, 400);
    });
  }
}
