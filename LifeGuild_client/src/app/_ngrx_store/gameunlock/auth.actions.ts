import { createAction, props } from '@ngrx/store';
import { User } from './model';

//when you login the props user is passed in
export const login = createAction(
  "[Login Page] User Login",
  props<{ user: User }>()
);



export const logout = createAction(
  "[Top Menu] Logout"
);
