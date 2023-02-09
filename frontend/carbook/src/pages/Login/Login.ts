import { Component } from "@/core";
import "./Login.scss";
import car from "@/assets/images/car.svg";
import { push } from "@/utils/router/navigate";

export default class LoginPage extends Component {
  template(): string {
    return /*html*/ `
    <div class="login__container">
      <header>
        <img src = ${car} class ='header-image'>
      <h1 class = 'header-logo'>CarBook</h1>
      <h3 class = 'header-message'>카북에 오신걸 환영합니다</h3>
      </header>
      <div class='login-contents'>
        <form class ='input-form'>
          <div class = 'login-id'> ID</div>
          <input type = 'text' placeholder='id를 입력해주세요' class ='input-box' name ='id'/>
          <div class ='login-password'> Password</div>
          <input type = 'password' class ='input-box' name = 'password' placeholder='비밀번호를 입력해주세요'/>
          <button type = 'submit' class ='input-form-button'>로그인</button>
        </form>
      </div>
      <footer>
        <h3 class ='footer-message'>계정이 없으신가요?</h3>
        <h3 class ='register'>회원 가입</h3>
      </footer>
    </div>
    `;
  }

  setEvent(): void {
    const form = document.body.querySelector(
      ".login__container .input-form"
    ) as HTMLFormElement;
    console.log(form);
    form?.addEventListener("submit", (e) => {
      e.preventDefault();

      const id = form.id.value.trim();
      const password = form.password.value.trim();

      const modal = document.body.querySelector(".alert-modal") as HTMLElement;

      // if (isEmpty(id, password, modal)) return false;

      push("/");
      return false;
    });
  }
}
