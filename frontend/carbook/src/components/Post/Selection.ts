import { Component } from '@/core';
import { qs } from '@/utils';

export default class Selection extends Component {
  setup(): void {
    this.state = {
      selected: '',
    };
  }
  template(): string {
    const { label, options } = this.props;

    return `
      <label>${label}</label>
      <select name="type" class="select">
          <option>선택하세요</option>
        ${options.map(
          (option: string) =>
            `
          <option value="${option}" 
          ${this.setSelected(option)} >${option}</option>`
        )}
      </select>
    `;
  }

  setEvent(): void {
    this.onHandleSelection();
  }

  onHandleSelection() {
    const selection = qs(this.$target, 'select') as HTMLSelectElement;
    selection.addEventListener('change', () => {
      const selected = selection.options[selection.selectedIndex].text;
      this.setState({ selected });
    });
  }

  setSelected(option: string) {
    const { selected } = this.state;
    return selected === option ? 'selected' : '';
  }
}
