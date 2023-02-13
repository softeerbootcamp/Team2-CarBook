import {IAction, createStore} from "@/store"


export const actionType = {
    FOLLOW: 'follow',
    UNFOLLOW: 'unfollow',
    DELETE_FOLLOWER:
  }

async function reducer(state: ITagObj = {}, action: IAction) {
    const { id, category, tag } = action.payload;
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

export const userStore = createStore(reducer);