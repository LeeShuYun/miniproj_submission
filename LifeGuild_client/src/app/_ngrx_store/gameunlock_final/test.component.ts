import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { distinctUntilChanged, map } from 'rxjs/operators';
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router } from '@angular/router';
import { AppState } from './index';
import { isLoggedIn, isLoggedOut } from './game.selectors';
import { isLocked, isUnlocked } from './game.actions';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent {
  isLocked$!: Observable<boolean>;
  isUnlocked$!: Observable<boolean>

  loading = true;

  isLoggedIn$!: Observable<boolean>;

  isLoggedOut$!: Observable<boolean>;

  constructor(private router: Router,
    private store: Store<AppState>) {

  }
  test(){
    
  }
  ngOnInit() {

    const userProfile = localStorage.getItem("user");

    // if (userProfile) {
    //   // this.store.dispatch(login({ user: JSON.parse(userProfile) }));
    // }

    this.router.events.subscribe(event => {
      switch (true) {
        case event instanceof NavigationStart: {
          this.loading = true;
          break;
        }

        case event instanceof NavigationEnd:
        case event instanceof NavigationCancel:
        case event instanceof NavigationError: {
          this.loading = false;
          break;
        }
        default: {
          break;
        }
      }
    });

    this.isLoggedIn$ = this.store
      .pipe(
        select(isLoggedIn)
      );

    this.isLoggedOut$ = this.store
      .pipe(
        select(isLoggedOut)
      );

  }

  logout() {

    // this.store.dispatch(logout());

  }
}
