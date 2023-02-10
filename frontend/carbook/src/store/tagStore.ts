import { CategoryType } from '@/interfaces';

interface ITagObj {
  [id: string]: {
    category?: CategoryType;
    tag?: string;
  };
}

interface IAction {
  type: string;
  tag: {
    id: string;
    category?: CategoryType;
    tag?: string;
  };
}

type handler = () => void;

export const actionType = {
  ADD_TAG: 'ADD_TAG',
  DELETE_TAG: 'DELETE_TAG',
};

function createStore(reducer: any) {
  let state: ITagObj = {};
  let handler: Array<handler> = [];
  return {
    dispach: async (action: IAction) => {
      state = await reducer(state, action);
      handler.forEach((callback) => {
        callback();
      });
    },
    subscribe: (listener: handler) => {
      handler.push(listener);
    },
    getState: () => {
      return state;
    },
  };
}

async function reducer(state: ITagObj = {}, action: IAction) {
  const { id, category, tag } = action.tag;
  switch (action.type) {
    case actionType.ADD_TAG:
      return { ...state, [id]: { category, tag } };
    case actionType.DELETE_TAG:
      delete state[id];
      return { ...state };
    default:
      throw new Error('올바른 action type이 아닙니다');
  }
}

export const tagStore = createStore(reducer);
