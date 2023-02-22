import {
  EMPTYNICKNAME,
  DUPPLICATEDNICKNAME,
  EMPTYPW,
  EMPTYMODIFYPW,
  EMPTYMODIFYCONFIRMPW,
  NotMatchedPassword,
} from '@/constants/errorMessage';

export function showErrorModal(modal: HTMLElement, errorMessage: string): void {
  if (modal.classList.contains('FadeInAndOut')) return;
  const mode =
    errorMessage === '비밀번호 변경에 성공하셨습니다' ? 'blue' : 'pink';
  modal.innerHTML = errorMessage;
  modal.classList.add(mode);
  modal.classList.toggle('FadeInAndOut');
  setTimeout(() => {
    modal.classList.toggle('FadeInAndOut');
    modal.classList.remove(mode);
  }, 2000);
}

export function IsInvalidNickname({
  alertModal,
  beforeNickname,
  newNickname,
}: {
  alertModal: HTMLElement;
  beforeNickname: string;
  newNickname: string;
}) {
  if (newNickname.length === 0) {
    showErrorModal(alertModal, EMPTYNICKNAME);
    return true;
  }
  if (newNickname === beforeNickname) {
    showErrorModal(alertModal, DUPPLICATEDNICKNAME);
    return true;
  }

  return false;
}

export function IsInvalidPassword({
  alertModal,
  beforePassword,
  afterPassword,
  afterPasswordConfirm,
}: {
  alertModal: HTMLElement;
  beforePassword: string;
  afterPassword: string;
  afterPasswordConfirm: string;
}) {
  if (beforePassword.length === 0) {
    showErrorModal(alertModal, EMPTYPW);
    return true;
  }

  if (afterPassword.length === 0) {
    showErrorModal(alertModal, EMPTYMODIFYPW);
    return true;
  }

  if (afterPasswordConfirm.length === 0) {
    showErrorModal(alertModal, EMPTYMODIFYCONFIRMPW);
    return true;
  }

  if (afterPassword !== afterPasswordConfirm) {
    showErrorModal(alertModal, NotMatchedPassword);
    return true;
  }

  if (beforePassword === afterPasswordConfirm) {
    showErrorModal(alertModal, '기존 비밀번호와 일치합니다');
    return true;
  }

  return false;
}
