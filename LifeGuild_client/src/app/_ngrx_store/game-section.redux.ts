import { createAction, createReducer, on } from '@ngrx/store';
import { Character } from '../_models/player';

/**
 * can't get this to workkkkk
 * TODO - will come back later :(
 */
export const gameLock = createAction('[Rewards Component] Lock Games Section');
export const gameUnlock = createAction('[Rewards Component] Unlock Games Section');

export interface GameModel {
  counter: number,
  isGameSectionLocked: boolean,
  character: Character
}
export interface State {
  counter: number,
  isGameSectionLocked: boolean,
  character: Character
}
export const initialState: State =
{
  counter: 0,
  isGameSectionLocked: true,
  character: {
    userid: 2,
    characterid: 2,
    health: 100,
    coinwallet: 0,
    currentpetid: 2,
    image: "character.avif"

  }
};

export const gameSectionReducer = createReducer(
  initialState,
  on(gameLock, (state) => {
    return { ...state, isGameUnlocked: true }
  }
  ),
  on(gameUnlock, (state) => {
    return { ...state, isGameSectionLocked: false }
  })
);


export const selectIsGameSectionLocked =
  (state: State) => state.isGameSectionLocked

export const selectCharacter =
  (state: State) => state.character;

export const selectCounter =
  (state: State) => state.counter;
