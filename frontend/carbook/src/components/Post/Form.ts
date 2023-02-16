import { Component } from '@/core';
import { ImageForm, Selection, HashTagForm, TextForm } from '@/components/Post';
import { getObjectKeyArray, qs, qsa, getHashTagsObj } from '@/utils';
import { basicAPI, formAPI } from '@/api';
import { push } from '@/utils/router/navigate';
import { POST_INIT } from '@/constants/post';
import { IPost, IPostIndex } from '@/interfaces';

export default class Form extends Component {
  postDetail: IPost = POST_INIT;

  setup(): void {
    const { prevPost } = this.props;
    this.postDetail = prevPost || POST_INIT;
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

  setEvent(): void {
    this.onClickBtnHandler();
  }

  onClickBtnHandler() {
    this.$target.addEventListener('click', (e: Event) => {
      const className = (<HTMLElement>e.target).className;

      switch (className) {
        case 'form__buttons--cancel':
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

    const { modelOption, typeOption } = await this.getCarOptions();
    new Selection(<HTMLElement>inputBoxs[0], {
      label: '차 종류',
      selected: type,
      options: typeOption,
      setFormData: (newData: any) => {
        this.setData(newData, 'type');
      },
    });
    new Selection(<HTMLElement>inputBoxs[1], {
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

  async getCarOptions() {
    try {
      const { models } = await (await basicAPI.get('/api/model')).data;
      const { types } = await (await basicAPI.get('/api/type')).data;

      const modelOption = models.map(({ tag }: { tag: string }) => tag);
      const typeOption = types.map(({ tag }: { tag: string }) => tag);

      return { modelOption, typeOption };
    } catch (error) {
      console.log(error);
      return { modelOption: [], typeOption: [] };
    }
  }

  async onSubmitHandler() {
    const { type, model, imageUrl, hashtags, content } = this.postDetail;
    const stringHashtags = getObjectKeyArray(hashtags).join(', ');

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
