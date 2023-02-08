import { Component } from "@/core";
import "./Signup.scss";
import car from "@/assets/images/car.svg";

export default class SignupPage extends Component {
  setup(): void {
    this.setState({
      // *더미 데이터
      id: "dongja",
      password: "qwer1234",
    });
  }
  template(): string {
    return `
    <div class = 'signup-container'>
    <header>
    <img src = ${car} class ='header-image'>
  <h1 class = 'header-logo'>CarBook</h1>
  <h3 class = 'header-message'>카북에 오신걸 환영합니다</h3>
  </header>
  <div class='signup-contents'>
    <form class ='input-form'>
      <div class = 'signup-id'> ID</div>
      <input type = 'text' placeholder='id를 입력해주세요' class ='input-box' name ='signup-id'/>
      <div class ='signup-password'> Password</div>
      <input type = 'password' class ='input-box' name = 'signup-password' placeholder='비밀번호를 입력해주세요' maxlength='16'/>
      <div class ='signup-nickname'> Nickname</div>
      <input type = 'nickname' class ='input-box' name = 'signup-nickname' placeholder='닉네임을 입력해주세요' maxlength='16'/>
      <button type = 'submit' class ='input-form-button'>회원가입</button>
    </form>
  </div>
  <footer>
    <h3 class ='footer-message'>계정이 이미 있으신가요?</h3>
    <h3 class ='footer-login'>로그인</h3>
  </footer>
  </div>`;
  }

  setEvent(): void {}
}
