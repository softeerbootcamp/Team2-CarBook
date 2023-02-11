export interface IAction {
  type: string;
  payload: {
    [property: string]: any;
  };
}

export type IListener = () => void;

export function createStore(reducer: any) {
  let state: object = {};
  let handler: object = {};
  return {
    dispach: async (action: IAction) => {
      state = await reducer(state, action);

      Object.entries(handler).forEach(([_, callback]) => {
        callback();
      });
    },
    subscribe: (component: object, listener: IListener) => {
      const componentName = component.constructor.name;
      handler = { ...handler, [componentName]: listener };
    },
    getState: () => {
      return state;
    },
  };
}
