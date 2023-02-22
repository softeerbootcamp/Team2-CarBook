import { CategoryType } from '@/interfaces';
import { IAction, createStore } from '@/store';

interface ITagObj {
  [id: string]: {
    category?: CategoryType;
    tag?: string;
  };
}

export const actionType = {
  ADD_TAG: 'ADD_TAG',
  DELETE_TAG: 'DELETE_TAG',
  CLEAR_TAG: 'CLEAR_TAG',
};

async function reducer(state: ITagObj = {}, action: IAction) {
  const { id, category, tag } = action.payload;
  switch (action.type) {
    case actionType.ADD_TAG:
      return { ...state, [id]: { category, tag } };
    case actionType.DELETE_TAG:
      delete state[id];
      return { ...state };
    case actionType.CLEAR_TAG:
      return {};
    default:
      throw new Error('올바른 action type이 아닙니다');
  }
}

export const tagStore = createStore(reducer);
