export const POST_INIT = {
  type: '',
  model: '',
  imageUrl: {},
  hashtags: [],
  content: '',
};

export const POST_LIST_INIT = {
  isLogin: localStorage.getItem('login') || false,
  nickname: '',
  isInit: true,
  isLoading: false,
  idEnd: false,
  images: [],
  length: 6,
  index: 0,
  tags: {},
};
