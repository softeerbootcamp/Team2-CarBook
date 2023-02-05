import { Component } from '@/core';
import Router from '@/utils/router';
import { LoginPage } from './pages';

export default class App extends Component {
  setup(): void {
    new Router(this.$target);
  }
  template(): string {
    const loginpage = new LoginPage(document.body);
    return loginpage.template();
  }
}
