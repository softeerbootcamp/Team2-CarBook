import { Component } from '@/core';
import './Post.scss';
import car from '@/assets/images/car.svg';
import search from '@/assets/icons/search-blue.svg';

export default class PostPage extends Component {
  template(): string {
    return `
    <div class="post-container">
      <header class="header">
        <div class="header__left-box">
          <div class="header__logo">CarBook</div>
          <div class="header__sub">새 게시물 등록하기</div>
        </div>
      </header>
      <section></section>
      <main class="main">
        <div class="wrapper">
        <section class="section">
          <img class="section__image" src="${car}" alt="logo"/>
          <div class="section__text">차 사진을<br />첨부해 주세요</div>
        </section>  
        <form class="form">
          <div class="form__input">
            <div class="input">
              <div class="input__text">차 정보</div>
              <div class="input__box">
                <label>차 종류</label>
                <select name="type">
                  <option>선택하세요</option>
                  <option>선택하세요</option>
                  <option>선택하세요</option>
                  <option>선택하세요</option>
                </select>
              </div>
              <div class="input__box">
                <label>차 모델명</label>
                <select class="select" name="model">
                  <option>선택하세요</option>
                  <option>선택하세요</option>
                  <option>선택하세요</option>
                  <option>선택하세요</option>
                </select>
              </div>
            </div>
            <div class="input">
              <div class="input__text">해시태그</div>
              <div class="input__box--hashtag">
                <div class="input__wrapper">
                  <input class="input" type="text" placeholder="해시태그를 검색해주세요"/>
                  <img src="${search}" alt="search-icon" class="icon"/>
                </div>
                <div class="hashtag__box">
                  <div class="hashtag"># car</div>
                  <div class="hashtag"># car</div>
                  <div class="hashtag"># car</div>
                </div> 
              </div>
            </div>
            <div class="input">
              <div class="input__text">글 내용</div>
              <textarea class="input__textarea" placeholder="글 내용을 입력해주세요"></textarea>
            </div>
          </div>
            <div class="form__buttons">
              <button class="form__buttons--cancel">취소</button>
              <button class="form__buttons--submit">생성</button>
            </div>
        </form>
        </div>
      </main>
    </div>
    `;
  }
}
