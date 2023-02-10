import { Component } from "@/core";
import { IImage } from "@/interfaces";

export default class Postlists extends Component {
  template(): string {
    const { images } = this.props;
    return /*html*/ `
      <h2 class = 'profile__contents-header'>
        내 게시물
      </h2>
      <div class="profile__contents-posts">
        ${images
          .map(
            (image: IImage) =>
              `<img class="profile__contents-post" src= '${image.imageUrl}'></img>`
          )
          .join("")}
      </div>
    `;
  }
}
