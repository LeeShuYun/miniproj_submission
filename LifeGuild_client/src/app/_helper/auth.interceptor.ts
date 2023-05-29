import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

//checks for jwt token if it has the correct auth
@Injectable()
export class BearerTokenInterceptor implements HttpInterceptor {
  constructor() {
    //check and get the jwt token from local
    this.jwt = localStorage.getItem("token") || "";
  }

  jwt!: string;

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // if (!!this.jwt) {
    //   //intercepts and signs all our outgoing requests with jwt token
    //   const setHeaders = { Authorization: `Bearer ${this.jwt}` };
    //   const authRequest = request.clone({ setHeaders });

    //   return next.handle(authRequest);
    // }

    return next.handle(request);
  }
}
