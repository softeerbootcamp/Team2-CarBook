import { tagStore } from '@/store/tagStore';

export const POST_INIT = {
  type: '',
  model: '',
  imageUrl: {},
  hashtags: [],
  content: '',
};

export const POST_LIST_INIT = {
  isInit: true,
  isLoading: false,
  idEnd: false,
  images: [],
  length: 6,
  index: 0,
  tags: tagStore.getState(),
};
