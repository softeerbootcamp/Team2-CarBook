import { Component } from "@/core";
import "./Login.scss";
import car from "@/assets/images/car.svg";
import { push } from "@/utils/router/navigate";
import { EMPTYID, EMPTYPW } from "@/constants/errorMessage";
import { basicAPI } from "@/api";
import {
  NONEXISTENTID,
  NONEXISTENTPW,
  LOGINSUCCESS,
} from "@/constants/errorMessage";

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
          <input type = 'email' placeholder='id를 입력해주세요' class ='input-box' name ='loginid'/>
          <div class ='login-password'> Password</div>
          <input type = 'password' class ='input-box' name = 'password' placeholder='비밀번호를 입력해주세요'/>
          <button type = 'submit' class ='input-form-button'>로그인</button>
        </form>
      </div>
      <footer class ='login-footer'>
        <h3 class ='footer-message'>계정이 없으신가요?</h3>
        <h3 class ='register'>회원 가입</h3>
      </footer>
      <div class = 'alert-modal'>오류 : 닉네임이 중복되었습니다.</div>
    </div>
    `;
  }

  setEvent(): void {
    const form = document.body.querySelector(
      ".login__container .input-form"
    ) as HTMLFormElement;
    form?.addEventListener("submit", (e) => {
      e.preventDefault();

      const id = form.loginid.value.trim();
      const password = form.password.value.trim();

      const modal = document.body.querySelector(".alert-modal") as HTMLElement;

      if (isEmpty(id, password, modal)) return false;

      sendUserInfo(id, password, modal);
      return false;
    });
  }

  mounted(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const signupLink = target.closest(".register");
      if (!signupLink) return;
      push("/signup");
    });
  }
}

/**
 * 1. id, email send to server
 * 2. server response success or fail
 * 3. 성공하면 메인페이지로 라우팅
 * 4. 실패하면 로그인 실패 경고창
 * */
async function sendUserInfo(
  email: string,
  password: string,
  modal: HTMLElement
) {
  await basicAPI
    .post("/api/login", {
      email,
      password,
    })
    .then((response) => {
      /**1. password error
       * 2. success
       */
      const TRANSITIONDELAY = 2300;
      const responseMessage = response.data.message;
      if (responseMessage === "ERROR: Password not match") {
        showErrorModal(modal, NONEXISTENTPW);
        return;
      }
      showErrorModal(modal, LOGINSUCCESS);
      setTimeout(() => {
        push("/");
      }, TRANSITIONDELAY);
    })
    .catch(() => {
      /**id error*/
      showErrorModal(modal, NONEXISTENTID);
    });
}

function showErrorModal(modal: HTMLElement, errorMessage: string): void {
  if (modal.classList.contains("FadeInAndOut")) return;
  modal.innerHTML = errorMessage;
  modal.classList.toggle("FadeInAndOut");
  setTimeout(() => {
    modal.classList.toggle("FadeInAndOut");
  }, 2000);
}

function isEmpty(id: string, password: string, modal: HTMLElement) {
  if (id === "") {
    showErrorModal(modal, EMPTYID);
    return true;
  }
  if (password === "") {
    showErrorModal(modal, EMPTYPW);
    return true;
  }
  return false;
}
