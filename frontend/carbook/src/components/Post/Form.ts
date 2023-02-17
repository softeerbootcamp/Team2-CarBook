import { Component } from '@/core';
import { ImageForm, Selection, HashTagForm, TextForm } from '@/components/Post';
import { qs, qsa, getHashTagsObj } from '@/utils';
import { basicAPI, formAPI } from '@/api';
import { push } from '@/utils/router/navigate';
import { POST_INIT } from '@/constants/post';
import { IPostIndex } from '@/interfaces';

export default class Form extends Component {
  postDetail: any;

  setup(): void {
    const { prevPost } = this.props;
    this.postDetail = prevPost || POST_INIT;

    this.onClickBtnHandler();
  }

  template(): string {
    return `
      <section class="section">
      </section>  
      <form class="form" enctype="multipart/form-data">
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

  onClickBtnHandler() {
    this.$target.addEventListener('click', (e: Event) => {
      const className = (<HTMLElement>e.target).className;

      switch (className) {
        case 'form__buttons--cancel':
          e.preventDefault();
          history.back();
          return;
        case 'form__buttons--submit':
          e.preventDefault();
          this.onSubmitHandler();
          return;
      }
    });
  }

  setData(prevData: string | string[], dataType: IPostIndex) {
    if (dataType === 'hashtags' && typeof prevData === 'object') {
      this.postDetail[dataType] = prevData;
    } else {
      if (dataType !== 'hashtags' && typeof prevData === 'string') {
        this.postDetail[dataType] = prevData;
      } else if (dataType === 'imageUrl' && typeof prevData === 'object') {
        this.postDetail[dataType] = prevData;
      }
    }
  }

  async mounted() {
    const section = qs(this.$target, '.section');
    const inputBoxs = qsa(this.$target, '.input__box');
    const hastagInput = qs(this.$target, '.tag');
    const textInput = qs(this.$target, '.text');

    const { type, model, imageUrl, hashtags, content } = this.postDetail;

    new ImageForm(section, {
      imageUrl,
      setFormData: (newData: any) => {
        this.setData(newData, 'imageUrl');
      },
    });

    const { typeOption, typeId } = await this.getTypeOptions(type);
    const modelOption = await this.getModelOptions(typeId);
    new Selection(<HTMLElement>inputBoxs[0], {
      label: '차 종류',
      selected: type,
      options: typeOption,
      setFormData: async (newData: any) => {
        this.setData(newData, 'type');
        const { typeId } = await this.getTypeOptions(newData);
        modelSelection.setState({
          options: await this.getModelOptions(typeId),
        });
      },
    });
    const modelSelection = new Selection(<HTMLElement>inputBoxs[1], {
      label: '차 모델',
      selected: model,
      options: modelOption,
      setFormData: (newData: any) => {
        this.setData(newData, 'model');
      },
    });
    new HashTagForm(hastagInput, {
      hashtags: getHashTagsObj(hashtags),
      setFormData: (newData: any) => {
        this.setData(newData, 'hashtags');
      },
    });
    new TextForm(textInput, {
      content,
      setFormData: (newData: any) => {
        this.setData(newData, 'content');
      },
    });
  }

  async getTypeOptions(typeName: string) {
    let typeId: number = 0;
    const { types } = await (await basicAPI.get('/api/type')).data;
    const typeOption = types.map(({ id, tag }: { id: number; tag: string }) => {
      if (tag === typeName) typeId = id;
      return tag;
    });
    return { typeOption, typeId };
  }

  async getModelOptions(id: number) {
    const { models } = await (await basicAPI.get('/api/model')).data;

    if (id) {
      const modelOption = models
        .filter(({ typeId }: { typeId: number }) => typeId === id)
        .map(({ tag }: { tag: string }) => tag);

      return modelOption || [];
    } else {
      const modelOption = models.map(({ tag }: { tag: string }) => tag);

      return modelOption || [];
    }
  }

  async onSubmitHandler() {
    const { type, model, imageUrl, hashtags, content } = this.postDetail;
    const stringHashtags = hashtags.join(', ');

    const formdata = new FormData();
    formdata.append('image', imageUrl);
    formdata.append('hashtag', stringHashtags);
    formdata.append('type', type);
    formdata.append('model', model);
    formdata.append('content', content);

    try {
      const { postId } = this.props;
      if (postId) {
        formdata.append('postId', postId);
        await formAPI.patch('/api/post', formdata);
        alert('글이 수정되었습니다.');
        push(`/post/${postId}`);
        return;
      }
      await formAPI.post('/api/post', formdata);
      alert('글이 생성되었습니다.');
      push('/');
    } catch (error) {
      console.error(error);
    }
  }
}
