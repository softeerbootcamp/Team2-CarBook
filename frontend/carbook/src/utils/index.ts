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

export function onChangeInputHandler(
  input: HTMLInputElement,
  callback: (value: string) => void
) {
  let timer: ReturnType<typeof setTimeout>;
  input?.addEventListener('keyup', () => {
    const { value } = input;

    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      callback(value);
    }, 200);
  });
}

export function onVisibleHandler(input: HTMLInputElement, className: string) {
  const isActive = document.activeElement;
  const dropdown = document.querySelector(className);

  if (isActive !== input) {
    dropdown?.classList.remove('active');
  } else {
    dropdown?.classList.add('active');
  }
}
