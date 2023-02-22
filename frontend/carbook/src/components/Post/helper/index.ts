export function getInitialPost() {
  return {
    type: '',
    model: '',
    imageUrl: {},
    hashtags: [],
    content: '',
  };
}

export function getResponse(status: number) {
  switch (status) {
    case 500:
      alert('잠시후 다시 시도해주세요');
      return;
    case 401:
      alert('다시 로그인 해주세요');
      return;
    default:
      alert('잠시후 다시 시도해주세요');
  }
}
