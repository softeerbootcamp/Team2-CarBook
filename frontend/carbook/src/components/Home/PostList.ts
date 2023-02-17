import { basicAPI } from '@/api';
import { POST_LIST_INIT } from '@/constants/post';
import { Component } from '@/core';
import { IImage } from '@/interfaces';
import { tagStore } from '@/store/tagStore';
import { getClosest, isSameObj, qs } from '@/utils';
import { push } from '@/utils/router/navigate';
import { getSearchUrl } from './helper';

export default class PostList extends Component {
  observer: any;
  lastImg: Element | null = null;

  setup(): void {
    this.lastImg = null;

    tagStore.subscribe(this, this.render.bind(this));

    this.state = POST_LIST_INIT;

    this.observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (
            entry.isIntersecting &&
            !this.state.isLoading &&
            !this.state.isEnd
          ) {
            this.fetchImages();
          }
        });
      },
      {
        threshold: 0.5,
      }
    );

    this.onClickImageHandeler();
  }

  render(): void {
    const { images, isLoading, isInit, tags } = this.state;

    if (!isSameObj(tags, tagStore.getState())) {
      this.$target.innerHTML = '';
      this.setState({
        ...POST_LIST_INIT,
        isLogin: this.state.isLogin,
        tags: tagStore.getState(),
      });

      return;
    }

    if (isInit) {
      this.fetchImages();
      return;
    }

    const spinner = qs(document, '.spinner');
    if (isLoading) {
      this.makeSkeleton();
      spinner.classList.add('active');
    } else {
      this.removeSkeleton();
      spinner.classList.remove('active');

      this.appendImages(images);
    }
  }

  async fetchImages() {
    const { index } = this.state;
    this.setState({ isLoading: true, isInit: false });

    const url = getSearchUrl(index);

    try {
      const res = await basicAPI.get(url);
      const { images, login, nickname } = res.data;
      const end = images.length === 0;

      if (login) {
        localStorage.setItem('login', login);
        this.props.setUserInfo(login, nickname);
      }

      this.setState({
        isLogin: localStorage.getItem('login'),
        images: this.state.images.concat(images),
        index: index + 8,
        isLoading: false,
        isEnd: end,
      });
    } catch (error) {
      console.error(error);
    }
  }

  makeSkeleton() {
    for (let i = 0; i < this.state.length; i++) {
      this.$target.insertAdjacentHTML(
        'beforeend',
        `
       <div class="gallery--skelton"></div>`
      );
    }
  }

  removeSkeleton() {
    const skeletons = this.$target.querySelectorAll('.gallery--skelton');

    skeletons.forEach((skel) => {
      this.$target.removeChild(skel);
    });
  }

  appendImages(images: []) {
    // src 수정 예정
    images.forEach(({ postId, imageUrl }: IImage) => {
      if (this.$target.querySelector(`img[data-id="${postId}"]`) === null) {
        this.$target.insertAdjacentHTML(
          'beforeend',
          ` <img class="gallery--image" src="${imageUrl}" data-id="${postId}"></img>`
        );
      }
    });

    const nextImg = this.$target.querySelector('img:last-child');
    if (nextImg !== null) {
      if (this.lastImg !== null) {
        this.observer.unobserve(this.lastImg);
      }
      this.lastImg = nextImg;
      this.observer.observe(this.lastImg);
    }
  }

  onClickImageHandeler() {
    this.$target.addEventListener('click', ({ target }) => {
      const image = getClosest(<HTMLElement>target, '.gallery--image');
      if (image) {
        if (this.state.isLogin) {
          const postId = image.dataset.id;
          push(`/post/${postId}`);
        } else {
          push('/login');
        }
      }
    });
  }
}
