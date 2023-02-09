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
            ${followButtonTemplate()}
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
      ${followinglistTemplate()}
      ${modal()}
    </div>
    `;
  }
}

function modal(): string {
  return /*html*/ `
  <div class ='modify-modal'>
    <div class = 'title'>회원 정보</div>
    <div class ='modify-modal-form'>
      <form>
        <div class ='modify-modal-form-nickname'>
        <h3 class = 'form-label'>닉네임</h3>
        <input class ='input-box' placeholder='현재 닉네임'/>
        </div>
        <div class ='modify-modal-form-nickname'>
        <h3 class = 'form-label'>비밀번호</h3>
        <input class ='input-box' placeholder='새 비밀번호'/>
        </div>
        <div class ='modify-modal-form-nickname'>
          <h3 class = 'form-label'>비밀번호 확인</h3>
          <input class ='input-box' placeholder='기존 비밀번호'/>
        </div>
        <div class ='modify-modal-footer'>
          <button class ='modify-button'>변경</button>
        </div>
      </form>
    </div>
  </div>
  `;
}

// function menuTemplate(): string {
//   return `<div class ='header-info-menu'>
//   <ul class = 'info-menu-items'>
//   <h3>설정</h3>
//   <li>닉네임 변경</li>
//   <li>비밀번호 변경</li>
//   <li>로그아웃</li>
// </ul>
// </div>`;
// }

function followButtonTemplate(): string {
  return `<button class = 'follow-button'>팔로우</button>`;
}

// function postslistTemplate(): string {
//   return /*html*/ `
//   <div class = 'profile__contents'>
//     <h2 class = 'profile__contents-header'>
//       내 게시물
//     </h2>
//     <div class="profile__contents-posts">
//       <div class="profile__contents-post"></div>
//       <div class="profile__contents-post"></div>
//       <div class="profile__contents-post"></div>
//       <div class="profile__contents-post"></div>
//       <div class="profile__contents-post"></div>
//       <div class="profile__contents-post"></div>
//     </div>
//   </div>
//   `;
// }

function followinglistTemplate(): string {
  return /*html*/ `
  <div class = 'profile__contents'>
  <h2 class = 'profile__contents-header'>
    팔로워
  </h2>
  <ul class = 'profile__contents-followers'>
    <li class = 'profile__contents-follower'>
      <div class ='follower-info'>
        <div class ='follower-info-icon'></div>
        <h3 class ='follower-info-nickname'>김은아</h3>
      </div>
      <button class ='follower-delete-button'>삭제</button>
    </li>
    <li class = 'profile__contents-follower'>
      <div class ='follower-info'>
        <div class ='follower-info-icon'></div>
        <h3 class ='follower-info-nickname'>김은아</h3>
      </div>
      <button class ='follower-delete-button'>삭제</button>
    </li>
    <li class = 'profile__contents-follower'>
      <div class ='follower-info'>
        <div class ='follower-info-icon'></div>
        <h3 class ='follower-info-nickname'>김은아</h3>
      </div>
      <button class ='follower-delete-button'>삭제</button>
    </li>
    <li class = 'profile__contents-follower'>
      <div class ='follower-info'>
        <div class ='follower-info-icon'></div>
        <h3 class ='follower-info-nickname'>김은아</h3>
      </div>
      <button class ='follower-delete-button'>삭제</button>
    </li>
  </ul>
</div>
  `;
}
