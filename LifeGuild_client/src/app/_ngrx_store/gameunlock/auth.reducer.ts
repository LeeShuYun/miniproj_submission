import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector, createReducer,
  createSelector,
  MetaReducer, on
} from '@ngrx/store';
import { User } from './model';
import * as AuthActions from './auth.actions';




export interface AuthState {
  user: User | undefined
}

export const initialAuthState: AuthState = {
  user: undefined
};

export const authReducer = createReducer(

  initialAuthState,

  on(AuthActions.login, (state, action) => {
    return {
      user: action.user
    }
  }),

  on(AuthActions.logout, (state, action) => {
    return {
      user: undefined
    }
  })



);

