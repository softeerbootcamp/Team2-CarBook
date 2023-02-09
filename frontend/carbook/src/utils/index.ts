import darkcar from '@/assets/icons/dark-car.svg';
import whitecar from '@/assets/icons/white-car.svg';

export function getTagIcon(type: string) {
  switch (type) {
    case 'model':
      return `<img src="${darkcar}" />`;
    case 'type':
      return `<img src="${whitecar}" />`;
    case 'hashtag':
      return '#';
    default:
      return;
  }
}
