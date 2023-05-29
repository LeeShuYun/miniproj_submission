import { createReducer, on } from '@ngrx/store';
import { increment, decrement, reset } from './counter.actions';

// StoreModule.forRoot({ count: counterReducer })],
//count is the initial state in here...
export const initialState = 0;

export const counterReducer = createReducer(
  initialState,
  on(increment, (state) => state + 1),
  on(decrement, (state) => state - 1),
  on(reset, (state) => 0)
);
