import { Component } from "@/core";
import "./Signup.scss";
import car from "@/assets/images/car.svg";
import { push } from "@/utils/router/navigate";
import {
  EMPTYID,
  EMPTYPW,
  EMPTYNICKNAME,
  DUPPLICATEDID,
  DUPPLICATEDNICKNAME,
  SUCCESSSIGNUP,
} from "@/constants/errorMessage";
import { basicAPI } from "@/api";

export default class SignupPage extends Component {
  template(): string {
    return /*html*/ `
    <div class = 'signup-container'>
    <header>
    <img src = ${car} class ='header-image'>
  <h1 class = 'header-logo'>CarBook</h1>
  <h3 class = 'header-message'>카북에 오신걸 환영합니다</h3>
  </header>
  <div class='signup-contents'>
    <form class ='input-form'>
      <div class = 'signup-id'> ID</div>
      <input type = 'email' placeholder='id를 입력해주세요' class ='input-box' name ='signupid' required autofocus/>
      <div class ='signup-password'> Password</div>
      <input type = 'password' class ='input-box' name = 'password' placeholder='비밀번호를 입력해주세요' maxlength='16'/>
      <div class ='signup-nickname'> Nickname</div>
      <input type = 'nickname' class ='input-box' name = 'nickname' placeholder='닉네임을 입력해주세요' maxlength='16'/>

      <button type = 'submit' class ='input-form-button'>회원가입</button>
    </form>
  </div>
  <footer>
    <h3 class ='footer-message'>계정이 이미 있으신가요?</h3>
    <h3 class ='footer-login'>로그인</h3>
  </footer>
  <div class = 'alert-modal'>오류 : 닉네임이 중복되었습니다.</div>
  </div>`;
  }

  setEvent(): void {
    const form = document.body.querySelector(
      ".signup-container .input-form"
    ) as HTMLFormElement;
    form?.addEventListener("submit", (e) => {
      e.preventDefault();

      const id = form.signupid.value.trim();
      const password = form.password.value.trim();
      const nickname = form.nickname.value.trim();

      const modal = document.body.querySelector(".alert-modal") as HTMLElement;

      if (isEmpty(id, password, nickname, modal)) return;
      sendUserInfo(id, password, nickname, modal);
    });
  }

  mounted(): void {
    this.$target.addEventListener("click", (e: Event) => {
      const target = e.target as HTMLElement;
      const loginLink = target.closest(".footer-login");
      if (!loginLink) return;
      push("/login");
    });
  }
}

async function sendUserInfo(
  email: string,
  password: string,
  nickname: string,
  modal: HTMLElement
) {
  await basicAPI
    .post("/api/signup", {
      email,
      password,
      nickname,
    })
    .then(() => {
      const LOGINDELAY = 2300;
      showErrorModal(modal, SUCCESSSIGNUP);
      setTimeout(() => {
        push("/login");
      }, LOGINDELAY);
    })
    .catch((error) => {
      if (error.response.data.message === "ERROR: Duplicated email") {
        showErrorModal(modal, DUPPLICATEDID);
        return;
      }
      if (error.response.data.message === "ERROR: Duplicated nickname")
        showErrorModal(modal, DUPPLICATEDNICKNAME);
    });
}

function showErrorModal(modal: HTMLElement, errorMessage: string): void {
  const FADEINOUTDELAY = 2000;
  const ModalStatus = errorMessage === SUCCESSSIGNUP ? "success" : "fail";

  if (modal.classList.contains("FadeInAndOut")) return;

  modal.innerHTML = errorMessage;
  modal.classList.toggle(ModalStatus);
  modal.classList.toggle("FadeInAndOut");

  setTimeout(() => {
    modal.classList.toggle("FadeInAndOut");
    modal.classList.toggle(ModalStatus);
  }, FADEINOUTDELAY);
}

function isEmpty(
  id: string,
  password: string,
  nickname: string,
  modal: HTMLElement
) {
  if (id === "") {
    showErrorModal(modal, EMPTYID);
    return true;
  }
  if (password === "") {
    showErrorModal(modal, EMPTYPW);
    return true;
  }
  if (nickname === "") {
    showErrorModal(modal, EMPTYNICKNAME);
    return true;
  }
  return false;
}
