import { createAction, props } from '@ngrx/store';
import { Player } from './model';


export const isLocked = createAction(
  "[Navbar] GameSection Is Locked",
  props<{ player: Player }>()
);



export const isUnlocked = createAction(
  "[Navbar] GameSection Is Unlocked"
);
