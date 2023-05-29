import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthState } from './auth.reducer';

//'auth' is the name of the variable in the store
//AuthState is a user key which can be defined | User obj
//so we fetch auth key into our AuthState
export const selectAuthState =
  createFeatureSelector<AuthState>("auth");

//isLoggedIn will return true if user is logged in
export const isLoggedIn = createSelector(
  selectAuthState,
  auth => !!auth.user // returns a boolean

);

// reverses the isLoggedIn
export const isLoggedOut = createSelector(
  isLoggedIn,
  loggedIn => !loggedIn
);
