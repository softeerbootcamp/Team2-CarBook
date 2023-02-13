import { Component } from '@/core';
import { ImageForm, Selection, HashTagForm, TextForm } from '@/components/Post';
import { qs, qsa } from '@/utils';

export default class Form extends Component {
  setup(): void {
    this.state = {
      type: '',
      model: '',
      image: '',
      hashtag: [],
      content: '',
    };
  }

  template(): string {
    return `
      <section class="section">
      </section>  
      <form class="form">
        <div class="form__input">
          <div class="input car">
            <div class="input__text">차 정보</div>
            <div class="input__box">
            </div>
            <div class="input__box">
            </div>
          </div>
          <div class="input tag">
          </div>
          <div class="input text">
          </div>
        </div>
        <div class="form__buttons">
          <button class="form__buttons--cancel">취소</button>
          <button class="form__buttons--submit">생성</button>
        </div>
      </form>
    `;
  }

  mounted(): void {
    const section = qs(this.$target, '.section');
    const inputBoxs = qsa(this.$target, '.input__box');
    const hastagInput = qs(this.$target, '.tag');
    const textInput = qs(this.$target, '.text');

    new ImageForm(section);
    new Selection(<HTMLElement>inputBoxs[0], {
      label: '차 종류',
      options: [],
    });
    new Selection(<HTMLElement>inputBoxs[1], {
      label: '차 모델',
      options: [],
    });
    new HashTagForm(hastagInput);
    new TextForm(textInput);
  }
}
