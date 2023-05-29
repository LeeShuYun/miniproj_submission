import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS, HttpErrorResponse } from '@angular/common/http';

import { StorageService } from '../_services/storage.service';
import { AuthService } from '../_services/auth.service';

import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

import { EventBusService } from '../_shared/event-bus.service';
import { EventData } from '../_shared/event.class';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  private isRefreshing = false;

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private eventBusService: EventBusService
  ) { }

  //checks for creds. HttpRequest holes url, body
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //edit the request to add with credentials
    req = req.clone({
      withCredentials: true,
    });

    //checks for the errors and returns the correct response
    return next.handle(req).pipe(
      catchError((error) => {
        if (
          //if it's a HttpErrorResponse and url contains auth/signin, and it's a 401
          error instanceof HttpErrorResponse &&
          !req.url.includes('auth/signin') &&
          error.status === 401
        ) {
          //pass to the helper func below
          return this.handle401Error(req, next);
        }
        //else push the error to consumer
        return throwError(() => error);
      })
    );
  }

  //specifically handles the 401
  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    //toggle the refresh. If isRefreshing is false, then we will refresh, else we just return the request
    if (!this.isRefreshing) {
      this.isRefreshing = true;

      //checks if user is logged in, if they are, call refreshToken()
      if (this.storageService.isLoggedIn()) {
        return this.authService.refreshToken().pipe(
          switchMap(() => {
            this.isRefreshing = false;

            return next.handle(request);
          }),
          catchError((error) => {
            this.isRefreshing = false;

            //if the api says refresh token is expired, emit logout event
            if (error.status == '403') {
              this.eventBusService.emit(new EventData('logout', null));
            }

            return throwError(() => error);
          })
        );
      }
    }

    return next.handle(request);
  }
}

//export the httpInterceptorProviders injection Token
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];
