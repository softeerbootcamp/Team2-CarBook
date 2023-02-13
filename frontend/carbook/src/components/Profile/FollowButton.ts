import { Component } from "@/core";

export default class FollowButton extends Component {
  template(): string {
    const { isFollow } = this.props;
    return `<button class = 'follow-button'>${
      isFollow ? "언팔로우" : "팔로우"
    }</button>`;
  }
}
