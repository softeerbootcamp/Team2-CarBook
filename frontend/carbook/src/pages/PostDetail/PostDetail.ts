import { Component } from '@/core';
import './PostDetail.scss';
import backButton from '@/assets/icons/backButton.svg';
import profile from '@/assets/icons/postdetail_profile.svg';
import menu from '@/assets/icons/postdetail_menu.svg';
import likeButton from '@/assets/icons/likeButton.svg';

export default class PostDetailPage extends Component {
  template(): string {
    return /*html*/ `
    <div class ='postdetail-container'>
      <div class = 'header'>
      </div>

      <div class ='postdetail-info'>
        <div class ='info-header'>
          <div class='info-profile'>
            <img class ='user-img' src = "${profile}"/>
            <h2 class = 'user-nickname'>유저 닉네임</h2>
          </div>
          <div class ='info-menu'><img src = "${menu}"/>
            <ul class = 'info-menu-items'>
              <li>메뉴</li>
              <li>수정</li>
              <li>삭제</li>
            </ul>
          </div>
        </div>
        <div class = 'info-contents'>
          <div class ='info-tag-cards'>
            <div class ='info-model-card'>차 모델명</div>
            <div class ='info-type-card'>차 종류</div>
          </div>
          <div class ='info-hashtags'>
            <div class ='info-hashtag'>#rolem</div>
            <div class ='info-hashtag'>#rolem</div>
            <div class ='info-hashtag'>#rolem</div>
          </div>
          <p class = 'info-description'>별하 다솜 여우비 로운 산들림 나비잠 감사합니다 책방 아리아 컴퓨터 가온해 늘품 도서 여우비 이플 도서관 나비잠 바나나 바람꽃 도서 바나나 예그리나 별하 소록소록 산들림 책방 바람꽃 소록소록 비나리 포도 감사합니다 함초롱하다 산들림 도담도담 바나나 가온누리 별하 산들림 그루잠 예그리나 도서 도서 바나나 함초롱하다 우리는 옅구름 도르레 산들림 옅구름 여우별.</p>
        </div>
      </div>
      
      <footer class ='postdetail-footer'>
        <div class ='like-info'>
          <div class ='like-button'><img src="${likeButton}"/></div>
          <div class ='like-count'>23 likes</div>
        </div>
        <div class ='posted-time'>2023.02.01. 11:03</div>
      </footer>

      <div class = 'back-button'> <img class = 'backbutton' src = "${backButton}"/> </div>
    </div>
    `;
  }
}
