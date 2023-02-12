export function getClosest(target: HTMLElement, name: string) {
  return target.closest(name) as HTMLElement;
}

export function qs(target: HTMLElement | Document, className: string) {
  return target.querySelector(className) as HTMLElement;
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
