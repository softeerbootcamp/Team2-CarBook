import { Component } from '@/core';
import Router from '@/utils/router';

export default class App extends Component {
  setup(): void {
    new Router(this.$target);
  }
}
