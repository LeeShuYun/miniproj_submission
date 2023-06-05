import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Token } from '@angular/compiler';
import { RegisterUserDetail } from '../_models/player';
import { API_URL } from '../_models/constant'

const headers = new HttpHeaders().set('Content-Type', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private httpClient: HttpClient) { }

  loginUser(email: string, password: string): Promise<any> {
    const body = { "email": email, "password": password };
    return firstValueFrom(this.httpClient.post<any>(`${API_URL}/api/auth/login`, body, { headers }));
  }

  async registerNewUser(userDetails: RegisterUserDetail): Promise<any> {
    const body = userDetails
    console.log("registerNewUser() => ", userDetails);
    return firstValueFrom(this.httpClient.put(`${API_URL}/api/auth/register`, body, { headers }));
  }
  //api/auth/register/confirm-email
  confirmEmail(email: string, code: number): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { "email": email, "code": code };
    console.log("registerNewUser() => ", body);
    return firstValueFrom(this.httpClient.put(`${API_URL}/api/auth/register/confirm-email`, body, { headers: headers }));
  }


}
