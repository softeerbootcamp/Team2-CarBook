import { Component } from '@/core';
import { ImageForm, Selection, HashTagForm, TextForm } from '@/components/Post';
import { qs, qsa } from '@/utils';
import { basicAPI } from '@/api';

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

  async mounted() {
    const section = qs(this.$target, '.section');
    const inputBoxs = qsa(this.$target, '.input__box');
    const hastagInput = qs(this.$target, '.tag');
    const textInput = qs(this.$target, '.text');

    new ImageForm(section);

    const { modelOption, typeOption } = await this.getCarOptions();
    new Selection(<HTMLElement>inputBoxs[0], {
      label: '차 종류',
      options: typeOption,
    });
    new Selection(<HTMLElement>inputBoxs[1], {
      label: '차 모델',
      options: modelOption,
    });
    new HashTagForm(hastagInput);
    new TextForm(textInput);
  }

  async getCarOptions() {
    try {
      const model = (await basicAPI.get('/api/model')).data();
      const type = (await basicAPI.get('/api/type')).data();

      const modelOption = model.types.map(({ tag }: { tag: string }) => tag);
      const typeOption = type.types.map(({ tag }: { tag: string }) => tag);

      return { modelOption, typeOption };
    } catch (error) {
      console.log(error);
      return { modelOption: [], typeOption: [] };
    }
  }
}
