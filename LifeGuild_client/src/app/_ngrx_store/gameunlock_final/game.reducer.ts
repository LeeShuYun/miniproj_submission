import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector, createReducer,
  createSelector,
  MetaReducer, on
} from '@ngrx/store';
import { Player } from './model';
import * as GameActions from './game.actions';




export interface GameState {
  player: Player | undefined
}

export const initialGameState: GameState = {
  player: undefined
};

export const gameReducer = createReducer(

  initialGameState,

  on(GameActions.isLocked, (state, action) => {
    return {
      player: action.player
    }
  }),

  on(GameActions.isUnlocked, (state, action) => {
    return { ...state,
      isGameUnlocked: 1
    }
  })



);

