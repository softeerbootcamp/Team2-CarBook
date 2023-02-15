import { WORD_LENGTH } from '@/constants/wordLength';

export function getClosest(target: HTMLElement, name: string) {
  return target.closest(name) as HTMLElement;
}

export function qs(target: HTMLElement | Document, className: string) {
  return target.querySelector(className) as HTMLElement;
}

export function qsa(target: HTMLElement | Document, className: string) {
  return target.querySelectorAll(className) as NodeList;
}

export function isEmptyObj(obj: object) {
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }

  return false;
}

export function isSameObj(obj1: object, obj2: object) {
  return Object.entries(obj1).toString() === Object.entries(obj2).toString();
}

export function getObjectKeyArray(object: object) {
  return Object.entries(object).map(([key, _]) => key);
}

export function onChangeInputHandler(
  input: HTMLInputElement | HTMLTextAreaElement,
  callback: (value: string) => void
) {
  let timer: ReturnType<typeof setTimeout>;
  input?.addEventListener('keyup', () => {
    const { value } = input;

    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      callback && callback(value);
    }, 400);
  });
}

export function onVisibleHandler(
  input: HTMLInputElement | HTMLTextAreaElement,
  className: string
) {
  const isActive = document.activeElement;
  const dropdown = document.querySelector(className);

  if (isActive !== input) {
    dropdown?.classList.remove('active');
  } else {
    dropdown?.classList.add('active');
  }
}

export function longKeywordHandler(keyword: string, plusNum?: number) {
  const korean = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
  if (plusNum === undefined) plusNum = 0;

  if (korean.test(keyword) && keyword.length > WORD_LENGTH.KOREAN + plusNum) {
    return keyword.substring(0, WORD_LENGTH.KOREAN + plusNum) + '...';
  } else if (keyword.length > WORD_LENGTH.ENGLISH + plusNum)
    return keyword.substring(0, WORD_LENGTH.ENGLISH + plusNum) + '...';

  return keyword;
}
