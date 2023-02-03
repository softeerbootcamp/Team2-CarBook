export default class Component {
  $target: HTMLElement;
  props: object;
  state: object = {};

  constructor($target: HTMLElement, props: object = {}) {
    this.$target = $target;
    this.props = props;
    this.setup();
    this.render();
  }
  setup() {}
  template() {
    return '';
  }
  render() {
    this.$target.innerHTML = this.template();
    this.setEvent();
  }
  setEvent() {}
  setState(newState: object) {
    this.state = { ...this.state, ...newState };
    this.render();
  }
}
