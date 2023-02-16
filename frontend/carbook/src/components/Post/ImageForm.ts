import { Component } from '@/core';
import car from '@/assets/images/car.svg';
import { isEmptyObj, qs } from '@/utils';
import axios from 'axios';

export default class ImageForm extends Component {
  setup(): void {
    this.state = {
      isInit: true,
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

  async setEvent() {
    const { isInit } = this.state;

    if (isInit) {
      const input = qs(this.$target, 'input');

      this.$target.addEventListener('click', () => {
        input.click();
      });
      input.addEventListener('change', this.getImageFiles.bind(this));
      this.setPrveImage();

      this.state.isInit = false;
    }
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

        console.log(file);

        this.setPreviewSrc(url);
      };
      reader.readAsDataURL(file);
    }
  }

  async setPrveImage() {
    const { imageUrl } = this.state;
    if (!isEmptyObj(imageUrl)) {
      this.setPreviewSrc(imageUrl);
      const file = await this.convertUrltoFile(imageUrl);
      this.props.setFormData(file);
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

  async convertUrltoFile(url: string) {
    const response = await axios.get<Blob>(url, {
      responseType: 'blob',
    });
    const newData = response.data;
    const ext = url.split('.').pop();
    const filename = url.split('/').pop();
    const metadata = { type: `image/${ext}` };

    return new File([newData], filename!, metadata);
  }
}
