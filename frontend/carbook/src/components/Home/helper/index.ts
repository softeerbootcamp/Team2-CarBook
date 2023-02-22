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
    all: [],
    hashtag: [],
    type: [],
    model: [],
  };

  for (const [_, value] of Object.entries(tagState)) {
    const { category, tag }: { category: CategoryType; tag: never } = value;
    filter[category].push(tag);
  }

  const { hashtag, type, model } = filter;

  const hashtagUrl = getTagUrl(hashtag, 'hashtag');
  const typeUrl = getTagUrl(type, 'type');
  const modelUrl = getTagUrl(model, 'model');

  const url = `/api/posts/m/search?index=${index}${hashtagUrl}${typeUrl}${modelUrl}`;

  return url;
}

function getTagUrl(tags: Array<string>, category: string) {
  return tags.length > 0 ? encodeURI(`&${category}=${tags.join('+')}`) : '';
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

export function getInitPostList() {
  return {
    isInit: true,
    isLoading: false,
    idEnd: false,
    images: [],
    length: 6,
    index: 0,
    tags: tagStore.getState(),
  };
}
