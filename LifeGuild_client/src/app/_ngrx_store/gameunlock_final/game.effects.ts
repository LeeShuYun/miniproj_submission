import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as GameActions from './game.actions';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';


@Injectable()
export class GameEffects {

  isLocked$ = createEffect(() =>
    this.actions$
      .pipe(
        ofType(GameActions.isLocked), //when it's triggered
        tap(action => localStorage.setItem('player',
          JSON.stringify(action.player))
        )
      )
    ,
    { dispatch: false });

  isUnlocked$ = createEffect(() =>
    this.actions$
      .pipe(
        ofType(GameActions.isUnlocked),
        tap(action => {
          localStorage.removeItem('player');
          // this.router.navigateByUrl('/login');
        })
      )
    , { dispatch: false });


  constructor(private actions$: Actions) {

  }

}
