import { createAction, props } from "@ngrx/store";
import { Character, PetInstance } from "../_models/player";

/**
 * global
 */

export const enterPetPage = createAction('[ Pet Page ] Enter');
export const enterHabitsPage = createAction('[ Habits Page ] Enter');
export const enterLandingPage = createAction('[ Landing Page ] Enter');

/**
 * global player Actions
 */

export const login = createAction(
  "[Landing Page] Load Character and Pet data",
  // props<{ character: Character, pet: PetInstance }>()
);

//gets the pet instance that the petid points to and changes the
//userid on it.
// then using the characterid, changes the character's currentpetid
export const updateCharacterPet = createAction(
  "[Habits Page] Update Character's Pet",
  props<{ characterid: number, petid: number }>()
);

/**
 * coinwallet actions. only habits page and pet page can do it
 */

export const deductCoinsFromFailingTask = createAction(
  "[Habits Page] deductCoins",
  props<{ amount: number }>()
);

export const addCoins = createAction(
  "[Habits Page] addCoins",
  props<{ amount: number }>()
);

export const deductCoinsFromPetAdoption = createAction(
  "[Pet Page] deductCoins",
  props<{ amount: number }>()
);

/**
 * reward actions - unlocking game section, needs to be global
 */
export const unlockGamesSection = createAction(
  "[Reward Component] unlockGamesSection"
);
export const lockGamesSection = createAction(
  "[Reward Component] lockGamesSection"
);








