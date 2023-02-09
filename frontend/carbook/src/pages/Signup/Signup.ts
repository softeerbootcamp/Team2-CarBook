import { Component } from "@/core";
import "./Signup.scss";
import car from "@/assets/images/car.svg";
import { push } from "@/utils/router/navigate";

export default class SignupPage extends Component {
  setup(): void {
    this.setState({
      // *더미 데이터
      userInfos: [
        {
          id: "jdh@naver.com",
          nickname: "dongja",
          password: "qwer1234",
        },
      ],
    });
  }
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
      <input type = 'email' placeholder='id를 입력해주세요' class ='input-box' name ='id' required autofocus/>
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

  showModal(modal: HTMLElement): void {
    modal.classList.toggle("FadeInAndOut");
    setTimeout(() => {
      modal.classList.toggle("FadeInAndOut");
    }, 3000);
  }

  setEvent(): void {
    const form = document.body.querySelector(
      ".signup-container .input-form"
    ) as HTMLFormElement;
    form?.addEventListener("submit", (e) => {
      e.preventDefault();

      const [
        EMPTYID,
        EMPTYPW,
        EMPTYNICKNAME,
        DUPPLICATEDID,
        DUPPLICATEDPW,
        DUPPLICATEDNICKNAME,
      ] = [
        "ID를 입력해주세요",
        "비밀번호를 입력해주세요",
        "닉네임을 입력해주세요",
        "중복된 ID입니다",
        "중복된 비밀번호입니다",
        "중복된 닉네임입니다",
      ];

      let isDupplicated = false;
      const id = form.id.value;
      const password = form.password.value;
      const nickname = form.nickname.value;

      const modal = document.body.querySelector(".alert-modal") as HTMLElement;
      console.log(modal);

      /**TODO 빈 입력값 체크 */
      if (id === "") {
        console.log("🍇empty id");
        modal.innerHTML = EMPTYID;
        this.showModal(modal);
        return false;
      }
      if (password === "") {
        console.log("🍇empty password");
        modal.innerHTML = EMPTYPW;
        this.showModal(modal);
        return false;
      }
      if (nickname === "") {
        console.log("🍇empty nickname");
        modal.innerHTML = EMPTYNICKNAME;
        this.showModal(modal);
        return false;
      }

      /**TODO 중복 값 체크 */
      this.state.userInfos.forEach((userInfo) => {
        if (userInfo.id === id) {
          console.log("💩dupplicated id");
          modal.innerHTML = DUPPLICATEDID;
          this.showModal(modal);
          isDupplicated = true;
          return false;
        }
        if (userInfo.password === password) {
          console.log("💩dupplicated password");
          modal.innerHTML = DUPPLICATEDPW;
          this.showModal(modal);
          isDupplicated = true;
          return false;
        }
        if (userInfo.nickname === nickname) {
          console.log("💩dupplicated nickname");
          modal.innerHTML = DUPPLICATEDNICKNAME;
          this.showModal(modal);
          isDupplicated = true;
          return false;
        }
      });
      push("/login");
      if (!isDupplicated) console.log("signup success");
      return false;
    });
  }
}
