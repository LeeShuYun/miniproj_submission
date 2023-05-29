import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Token } from '@angular/compiler';
import { RegisterUserDetail } from '../_models/player';
import { API_URL } from '../_models/constant'

// const AUTH_API = 'http://localhost:8080/api/auth';
// const AUTH_API = 'https://miniproj-server-production.up.railway.app/api/v1/auth';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn: boolean = false;

  constructor(
    private httpClient: HttpClient) { }

  ///api/v1/auth/login
  loginUser(email: string, password: string): Promise<any> {
    // const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body = { "email": email, "password": password };
    return firstValueFrom(this.httpClient.post<any>(`${API_URL}/api/auth/login`, body));
  }

  //api/vi/auth/register
  async registerNewUser(userDetails: RegisterUserDetail): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    console.log("registerNewUser() => ", userDetails);
    return firstValueFrom(this.httpClient.put(`${API_URL}/api/auth/register`, { body: userDetails, headers: headers }));
  }
  //api/auth/register/confirm-email
  confirmEmail(email: string, code: number): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { "email": email, "code": code };
    console.log("registerNewUser() => ", body);
    return firstValueFrom(this.httpClient.put(`${API_URL}/api/auth/register/confirm-email`, { body, headers: headers }));
  }

  // calcContentLength(content: string) {
  //   const encoder = new TextEncoder();
  //   const encodedstr = (encoder.encode(content)).length
  //   console.log(encodedstr)
  //   return encodedstr.toString();
  // }
  //USER LOGOUT ==============================================

  //USER LOGIN JWT Only version ==========================================
  // loginUserJWT(email: string, password: string): Promise<any> {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  //   //send user detail for verification
  //   const body = { email, password };
  //   return firstValueFrom(this.httpClient.post<string>(AUTH_API + '/login', body, { headers }));
  // }

  //USER LOGIN GOOGLE VERSION =========================================
  // loginUserGoogle() {
  //   // return firstValueFrom(this.httpClient.post<string>(AUTH_API + '/login', { headers, body }));
  // }
  // USER LOGIN OAUTH version TODO =======================================
  // refreshToken() {
  //   return this.httpClient.post(AUTH_API + 'refreshtoken', {}, httpOptions);
  // }
  // 1. POST /api/auth/signin + { username, password }
  // 2. receive {user info, authorities} + JWT accessToken, refreshToken in HttpOnlyCookie
  // login(email: string, password: string) {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
  //   return this.httpClient.post<User>('/api/login', { email, password })
  //     // this is just the HTTP call,
  //     // we still need to handle the reception of the token
  //     .shareReplay();

  //   //sharereplay prevents the receiver of this Observable from accidentally triggering multiple POST requests
  // }

  // ACCESS RESOURCE WITH EXPIRED TOKEN
  // 1. REQUEST with JWT in HttpOnly Cookie
  // 2. receive 401 and Token Expired Message
  // 3. Send refresh token request POST api/auth/refreshtoken + Body refreshToken in HttpOnly Cookie
  // 4. receive new JWT accessToken, refreshToken in HttpOnly Cookie
  // 5. request data with new JWT in HttpOnly Cookie

  // USER LOGIN
  // 1. POST /api/auth/signin + { username, password }
  // 2. receive {user info, authorities} + JWT accessToken, refreshToken in HttpOnlyCookie
  // signInWithEmail(user: User) {

  // }
  // ACCESS RESOURCE WITH EXPIRED TOKEN
  // 1. REQUEST with JWT in HttpOnly Cookie
  // 2. receive 401 and Token Expired Message
  // 3. Send refresh token request POST api/auth/refreshtoken + Body refreshToken in HttpOnly Cookie
  // 4. receive new JWT accessToken, refreshToken in HttpOnly Cookie
  // 5. request data with new JWT in HttpOnly Cookie


  /// ================= old stuff =======================
  //GET /api/login?query=<user>
  // getUser(query: string): Promise<any> {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  //   const params = new HttpParams()
  //     .set("query", query);
  //   return lastValueFrom(
  //     // this.httpClient.get('http://localhost:8080/api/search', { params: params, headers: headers }));
  //     this.httpClient.get<User[]>('/api/login', { params: params, headers: headers }));
  // }




  // //POST /api/auth/signup
  // registerNewUser(newUserDetail: RegisterUserDetail) {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  //   //add the newUserDetail into the body
  //   const body = new HttpParams().appendAll({ ...newUserDetail })
  //   console.log("addUser(): posting newUser>> ", body)

  //   return lastValueFrom(this.httpClient.post<RegisterUserDetail>("/api/auth/signup", body.toString(), { headers }))
  // }
}
