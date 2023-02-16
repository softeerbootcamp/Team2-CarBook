import { Component } from '@/core';
import car from '@/assets/images/car.svg';
import { qs } from '@/utils';

export default class ImageForm extends Component {
  setup(): void {
    this.state = {
      imageUrl: this.props.imageUrl,
    };
  }
  template(): string {
    return `
      <input type="file" accept="image/*"  style="display: none;">
      <img class="section__image" src="${car}" alt="logo"/>
      <div class="section__text"><span>*</span>차 사진을<br />첨부해 주세요</div>
    `;
  }

  setEvent(): void {
    const { imageUrl } = this.state;
    const input = qs(this.$target, 'input');

    this.$target.addEventListener('click', () => {
      input.click();
    });
    input.addEventListener('change', this.getImageFiles.bind(this));
    if (imageUrl) this.setPreviewSrc(imageUrl);
  }

  getImageFiles(e: Event) {
    const files = (<HTMLInputElement>e.currentTarget).files;

    if (files?.length === 1) {
      const file = files[0];

      const reader = new FileReader();
      reader.onload = (e) => {
        const url = (<FileReader>e.target).result as string;
        this.setState({ imageUrl: url });
        this.props.setFormData(file);

        this.setPreviewSrc(url);
      };
      reader.readAsDataURL(file);
    }
  }

  setPreviewSrc(url: string) {
    this.$target.style.backgroundImage = `url('${url}')`;
    this.hideInnerContent();
  }

  hideInnerContent() {
    const img = qs(this.$target, '.section__image');
    const text = qs(this.$target, '.section__text');

    img.classList.add('hide');
    text.classList.add('hide');
  }
}
