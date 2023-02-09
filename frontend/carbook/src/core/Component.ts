interface IObject {
  [property: string]: any;
}

export default class Component {
  $target: HTMLElement;
  props: IObject;
  state: IObject = {};

  constructor($target: HTMLElement, props: object = {}) {
    this.$target = $target;
    this.props = props;
    this.setup();
    this.render();
  }
  setup() {}
  template() {
    return "";
  }
  render() {
    this.$target.innerHTML = this.template();
    this.setEvent();
    this.mounted();
  }
  mounted() {}
  setEvent() {}
  setState(newState: object) {
    this.state = { ...this.state, ...newState };
    this.render();
  }
}
