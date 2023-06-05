import { createReducer, on } from "@ngrx/store";
import { Character } from "../_models/player";
import * as PlayerActions from "../_ngrx_store/player.actions";

/**
 * listens for player related action and selects the data in the store
 * selectors are at the bottom
 */

//REDUCER ====================================================
export interface CharacterState {
  character: Character;
}

export const initialState: CharacterState = {
  character: {
    userid: "000001",
    characterid: 0,
    health: 0,
    coinwallet: 0,
    currentpetid: 0,
    image: "character.avif"
  }
}

export const characterReducer = createReducer(
  initialState
  ,
  on(
    PlayerActions.enterPetPage,
    PlayerActions.enterHabitsPage,
    PlayerActions.enterLandingPage,
    (state) => {
      return {
        ...state,
        selectedPetId: null
      }
    })
  ,
  on(PlayerActions.updateCharacterPet, (state, action) => {
    return {
      ...state,
      petid: action.petid
    }
  })
  ,
  on(PlayerActions.deductCoinsFromFailingTask, (state, action) => {
    return {
      ...state,
      character: updateCharacterCoins(state.character, action.amount * -1)
    }
  })
  ,
  on(PlayerActions.addCoins, (state, action) => {
    return {
      ...state,
      character: updateCharacterCoins(state.character, action.amount)
    }
  })
  ,
  on(PlayerActions.deductCoinsFromPetAdoption, (state, action) => {
    return {
      ...state,
      character: updateCharacterCoins(state.character, action.amount * -1)
    }
  })



)

const updateCharacterCoins = (character: Character, amount: number) => {
  const coinwallet = character.coinwallet + amount
  //merges the coinwallet value with character into new Character obj
  return Object.assign({}, character, { coinwallet });
}

//SELECTORS =================================================
//not really using the full power of ngrx memoisation, but this will come in handy
export const selectCharacterFromStore = (state: CharacterState) => { state.character }
