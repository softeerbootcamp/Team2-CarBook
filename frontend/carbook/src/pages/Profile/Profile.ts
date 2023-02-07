import { Component } from '@/core';
import './Profile.scss';

export default class ProfilePage extends Component {
  template(): string {
    return /*html*/ `
    <div class = 'profile__container'>
      <header>
        <div class ='header-info'>
          <div class = 'header-info-header'>
            <div class ='user-nickname'>유저 닉네임</div>
            <div class ='header-info-menu'></div>
          </div>
          <div class ='user-email'>test@gmail.com</div>
          
        </div>
        <div class ='header-contents'>
          <section class ='profile-posts'>
            <div class ='profile-posts-title'>게시물</div>
            <div class ='profile-posts-count'>13</div>
          </section>
          <section class ='profile-follower'>
            <div class ='profile-follower-title'>팔로워</div>
            <div class ='profile-follower-count'>164</div>
          </section>
          <section class ='profile-following'>
            <div class ='profile-following-title'>팔로잉</div>
            <div class ='profile-following-count'>272</div>
          </section>
        </div>
      </header>
      <div class = 'profile__contents'>
        <h2 class = 'profile__contents-header'>
          내 게시물
        </h2>
        <div>
        <div class="main__gallery">
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
          <div class="main__gallery--image"></div>
      </div>
        </div>
      </div>
    </div>
    `;
  }
}
