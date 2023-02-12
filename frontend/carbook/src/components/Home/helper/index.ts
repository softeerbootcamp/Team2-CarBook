import { CategoryType } from '@/interfaces';
import { tagStore } from '@/store/tagStore';
import darkcar from '@/assets/icons/dark-car.svg';
import whitecar from '@/assets/icons/white-car.svg';
import { isEmptyObj } from '@/utils';

export function getSearchUrl(index: string) {
  const tagState = tagStore.getState();
  if (isEmptyObj(tagState)) {
    return `/api/posts/m?index=${index}`;
  }

  const filter = {
    hashtag: [],
    type: [],
    model: [],
  };

  for (const [_, value] of Object.entries(tagState)) {
    const { category, tag }: { category: CategoryType; tag: never } = value;
    filter[category].push(tag);
  }

  const { hashtag, type, model } = filter;
  const url = `/api/posts/m/search?index=${index}&hastag=${hashtag.join(
    '+'
  )}&type=${type.join('+')}&model=${model.join('+')}`;

  return url;
}

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
