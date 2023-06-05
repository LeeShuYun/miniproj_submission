import { createReducer, on } from "@ngrx/store";
import { Character, PetInstance } from "../_models/player";
import * as PlayerActions from "../_ngrx_store/player.actions";

/**
 * listens for player related action and selects the data in the store
 * selectors are at the bottom
 */

//REDUCER ====================================================
export interface State {
  character: Character;
  pet: PetInstance;
  selectedPetId: number | null;
  isGamesSectionUnlocked: boolean;
}

export const initialState: State = {
  character: {
    userid: "000002",
    characterid: 0,
    health: 0,
    coinwallet: 0,
    currentpetid: 0,
    image: "character.avif"
  },
  pet: {
    petId: 0,
    userid: "000002",
    image: "R2D2.avif",
    healing: 0
  },
  selectedPetId: null,
  isGamesSectionUnlocked: true
}

export const playerReducer = createReducer(
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
  // ,
  // on(PlayerActions.updateCharacterPet, (state, action) => {
  //   selectCharacterFromStore,
  //     selectPlayerPetFromStore,
  //     selectOtherPetInstance,
  //     (character, pet, otherPet) => {
  //       return {
  //         ...state,
  //         characterid: action.characterid,
  //         pet: action.petid
  //       }
  //     }
  // })
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
  ,
  on(PlayerActions.unlockGamesSection, (state) => {
    return {
      ...state,
      isGamesSectionUnlocked: true
    }
  })
  ,
  on(PlayerActions.lockGamesSection, (state) => {
    return {
      ...state,
      isGamesSectionUnlocked: false
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
export const selectCharacterFromStore = (state: State) => { state.character }
export const selectPlayerPetFromStore = (state: State) => { state.pet }
// export const selectActiveTradeablePetIdFromStore = (state: State) => { state.selectedPetId }
export const selectIsGamesSectionUnlockedFromStore = (state: State) => { state.isGamesSectionUnlocked }

