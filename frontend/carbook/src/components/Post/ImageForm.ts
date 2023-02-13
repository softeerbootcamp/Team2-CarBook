import { Component } from '@/core';
import car from '@/assets/images/car.svg';

export default class ImageForm extends Component {
  template(): string {
    return `
      <img class="section__image" src="${car}" alt="logo"/>
      <div class="section__text">차 사진을<br />첨부해 주세요</div>
    `;
  }
}
