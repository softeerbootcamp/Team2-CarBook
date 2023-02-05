import { ROUTE_CHNAGE_EVENT } from '@/constants/eventName';

const navigate = (nextUrl: string, isReplace = false) => {
  window.dispatchEvent(
    new CustomEvent(ROUTE_CHNAGE_EVENT, {
      detail: {
        nextUrl,
        isReplace,
      },
    }),
  );
};

export const push = (nextUrl: string) => {
  navigate(nextUrl);
};
export const replace = (nextUrl: string) => {
  navigate(nextUrl, true);
};
