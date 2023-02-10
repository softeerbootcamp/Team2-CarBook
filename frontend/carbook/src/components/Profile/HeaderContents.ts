import { Component } from "@/core";

export default class HeaderContents extends Component {
  template(): string {
    const { posts, follower, following } = this.props;
    return /*html*/ `
        <section class ='profile-posts'>
          <div class ='profile-posts-title'>게시물</div>
          <div class ='profile-posts-count'>${posts}</div>
        </section>
        <section class ='profile-follower'>
          <div class ='profile-follower-title'>팔로워</div>
          <div class ='profile-follower-count'>${follower}</div>
        </section>
        <section class ='profile-following'>
          <div class ='profile-following-title'>팔로잉</div>
          <div class ='profile-following-count'>${following}</div>
        </section>
        `;
  }
}
