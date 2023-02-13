import { Component } from '@/core';
import car from '@/assets/images/car.svg';
import { qs } from '@/utils';

export default class ImageForm extends Component {
  setup(): void {
    this.state = {
      imageFile: null,
    };
  }
  template(): string {
    return `
      <input type="file" accept="image/*" style="display: none;">
      <img class="section__image" src="${car}" alt="logo"/>
      <div class="section__text">차 사진을<br />첨부해 주세요</div>
    `;
  }

  setEvent(): void {
    const input = qs(this.$target, 'input');

    this.$target.addEventListener('touchstart', () => {
      input.click();
    });
    input.addEventListener('change', this.getImageFiles.bind(this));
  }

  getImageFiles(e: Event) {
    const files = (<HTMLInputElement>e.currentTarget).files;

    if (files?.length === 1) {
      const file = files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        this.setPreviewSrc(e, file);
      };
      reader.readAsDataURL(file);
    }
  }

  setPreviewSrc(e: Event, file: File) {
    const url = (<FileReader>e.target).result;
    this.$target.style.backgroundImage = `url(${url})`;
    this.$target.setAttribute('data-file', file.name);
    this.setState({ imageFile: url });

    this.hideInnerContent();
  }

  hideInnerContent() {
    const img = qs(this.$target, '.section__image');
    const text = qs(this.$target, '.section__text');

    img.classList.add('hide');
    text.classList.add('hide');
  }
}
