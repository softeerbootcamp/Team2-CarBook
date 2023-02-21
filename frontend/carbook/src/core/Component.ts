import { isSameObj } from "@/utils";

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
    this.setEvent();
  }
  setup() {}
  template() {
    return "";
  }
  render() {
    this.$target.innerHTML = this.template();
    this.mounted();
  }
  mounted() {}
  setEvent() {}
  setState(newState: object) {
    const prevState = this.state;
    const nextState = { ...this.state, ...newState };
    if (isSameObj(prevState, nextState)) return;
    this.state = nextState;
    this.render();
  }
}
