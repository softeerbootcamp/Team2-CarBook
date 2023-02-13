import { Component } from '@/core';

export default class Selection extends Component {
  template(): string {
    const { label, options } = this.props;

    return `
      <label>${label}</label>
      <select name="type">
        ${options.map(
          (option: string) => `
          <option>${option}</option>
        `
        )}
      </select>
    `;
  }
}
