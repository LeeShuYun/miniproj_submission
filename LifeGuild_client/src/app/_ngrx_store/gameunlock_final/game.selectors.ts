import { createFeatureSelector, createSelector } from '@ngrx/store';
import { GameState } from './game.reducer';


export const selectGameState =
  createFeatureSelector<GameState>("game");


export const isLoggedIn = createSelector(
  selectGameState,
  game => !!game.player

);


export const isLoggedOut = createSelector(
  isLoggedIn,
  loggedIn => !loggedIn
);
