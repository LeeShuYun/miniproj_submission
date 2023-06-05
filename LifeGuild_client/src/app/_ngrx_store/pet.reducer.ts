import { createReducer, on } from "@ngrx/store";
import { Character, PetInstance } from "../_models/player";
import * as PlayerActions from "../_ngrx_store/player.actions";

/**
 * listens for player related action and selects the data in the store
 * selectors are at the bottom
 */

//REDUCER ====================================================
export interface PetState {
  petId: number,
  userid: string,
  image: string,
  healing: number
}

export const initialState: PetState = {
  petId: 2,
  userid: "000002",
  image: "dragon.avif",
  healing: 5
}

export const petReducer = createReducer(
  initialState,
  on(PlayerActions.updateCharacterPet, (state, action) => {
    return {
      ...state,
      // userid: action.userid
    }
  })



)

//SELECTORS =================================================
//not really using the full power of ngrx memoisation, but this will come in handy
export const selectPlayerPetFromStore = (state: PetState) => { state.petId }
// export const selectActiveTradeablePetIdFromStore = (state: PetState) => { state.selectedPetId }

