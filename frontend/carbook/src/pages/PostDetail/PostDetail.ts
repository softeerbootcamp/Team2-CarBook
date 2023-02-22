import { Component } from '@/core';
import './PostDetail.scss';
import backButton from '@/assets/icons/backButton.svg';
import { InfoContents, InfoHeader, Footer } from '@/components/PostDetail';
import { basicAPI } from '@/api';
import isLogin from '@/utils/isLogin';
import { push } from '@/utils/router/navigate';
import { actionType, tagStore } from '@/store/tagStore';

export default class PostDetailPage extends Component {
  async setup() {
    const login = await isLogin();
    if (!login) push('/login');
    this.state.isloading = true;
    const postid = location.pathname.split('/').slice(-1)[0];
    this.state.postid = postid;
    await this.fetchPostDefail(postid);
    const postDetailContainer = this.$target.querySelector(
      '.postdetail-container'
    ) as HTMLElement;

    postDetailContainer.addEventListener('click', (e: Event) => {
      const target = e.target as HTMLElement;
      const closeButton = target.closest('.close') as HTMLElement;
      const imageModal = postDetailContainer.querySelector(
        '.image-modal'
      ) as HTMLElement;

      if (closeButton) {
        imageModal.classList.toggle('FadeInAndOut');
        return;
      }

      //tag 클릭했을 때
      const typeTag = target.closest('.info-type-card') as HTMLElement;
      const modelTag = target.closest('.info-model-card') as HTMLElement;
      const hashTag = target.closest('.info-hashtag') as HTMLElement;

      if (typeTag || modelTag || hashTag) {
        localStorage.setItem('images', '[]');

        if (typeTag) {
          const category = 'type';
          const tag = typeTag.dataset.tag as string;
          const id = (category + tag).replaceAll(' ', '');

          this.addTag({ category, tag, id });
        }

        if (modelTag) {
          const category = 'model';
          const tag = modelTag.dataset.tag as string;
          const id = (category + tag).replaceAll(' ', '');

          this.addTag({ category, tag, id });
        }

        if (hashTag) {
          const category = 'hashtag';
          const tag = hashTag.dataset.tag as string;
          const id = (category + tag).replaceAll(' ', '');

          this.addTag({ category, tag, id });
        }

        push('/');
        return;
      }

      const header = target.closest('.header') as HTMLElement;

      if (!header) return;

      const modalContent = postDetailContainer.querySelector(
        '.modal-content'
      ) as HTMLImageElement;

      modalContent.src = this.state.imageUrl;
      imageModal.classList.toggle('FadeInAndOut');
    });
  }

  addTag({ id, category, tag }: { id: string; category: string; tag: string }) {
    if (!(id && category && tag)) return;
    tagStore.dispach({ type: actionType.CLEAR_TAG, payload: {} });
    tagStore.dispach({
      type: actionType.ADD_TAG,
      payload: { id, category, tag },
    });
  }

  async fetchPostDefail(postId: string) {
    const data = await basicAPI
      .get(`/api/post?postId=${postId}`)
      .then((response) => response.data)
      .catch((error) => {
        const errorMessage = error.response.data.message;
        console.log(errorMessage);
      });
    this.setState({
      ...this.state,
      ...data,
      isloading: false,
    });
  }

  template(): string {
    return /*html*/ `
    <div class ='postdetail-container'>
      <div class = 'header'></div>

      <div class ='postdetail-info'>
        <div class ='info-header'>
        </div>
        <div class = 'info-contents'>
        </div>
      </div>
      
      <footer class ='postdetail-footer'>
      </footer>

      <div class = 'back-button'> <img class = 'backbutton' src = "${backButton}"/> </div>

      <div class = 'alert-modal'></div>
      <div class ='image-modal'>
        <span class ='close'>x</span>
        <img class ='modal-content'>
      </div>
    </div>
    `;
  }

  render(): void {
    if (this.state?.isloading) return;
    this.$target.innerHTML = this.template();

    const postDefailContainer = this.$target.querySelector(
      '.postdetail-container'
    ) as HTMLElement;

    if (postDefailContainer.classList.contains('once')) return;
    postDefailContainer.classList.add('once');
    postDefailContainer.addEventListener('click', (e: Event) => {
      const target = e.target as HTMLElement;

      if (target.classList.contains('info-menu')) {
        const menuItems = target.querySelector(
          '.info-menu-items'
        ) as HTMLElement;
        menuItems.classList.toggle('FadeInAndOut');
        return;
      }

      if (target.closest('.back-button')) {
        history.back();
        return;
      }

      if (target.closest('.like-button')) {
        this.fetchlike(this.state.postid);
      }
    });

    this.setEvent();
    this.mounted();
  }

  mounted(): void {
    const postImg = this.$target.querySelector('.header') as HTMLElement;
    const infoHeader = this.$target.querySelector(
      '.info-header'
    ) as HTMLElement;
    const infoContents = this.$target.querySelector(
      '.info-contents'
    ) as HTMLElement;
    const footer = this.$target.querySelector(
      '.postdetail-footer'
    ) as HTMLElement;

    postImg.style.backgroundImage = `linear-gradient(180deg, rgba(0, 0, 0, 0.2) 0%, rgba(0, 0, 0, 0) 100%), url(${this.state.imageUrl}) `;
    new Footer(footer, {
      like: this.state.like,
      likeCount: this.state.likeCount,
      createDate: this.state.createDate,
      updateDate: this.state.updateDate,
    });
    new InfoContents(infoContents, {
      type: this.state.type,
      model: this.state.model,
      content: this.state.content,
      hashtags: this.state.hashtags,
    });
    new InfoHeader(infoHeader, {
      nickname: this.state.nickname,
      isMyPost: this.state.myPost,
    });
  }

  async fetchlike(id: string) {
    const postId = parseInt(id);
    console.log('start fetchlike');
    await basicAPI
      .post(`/api/post/like`, { postId })
      .then((response) => {
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        const errorMessage = error.response.data.message;
        console.log(error);
        console.log(errorMessage);
      });
    await this.fetchPostDefail(id);
  }
}
