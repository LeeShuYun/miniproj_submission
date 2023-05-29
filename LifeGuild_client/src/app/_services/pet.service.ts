import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { firstValueFrom, lastValueFrom } from 'rxjs';
import { PetBlueprint } from '../_models/player';
import { API_URL } from '../_models/constant'

@Injectable({
  providedIn: 'root'
})
export class PetService {

  constructor(private httpClient: HttpClient) { }

  // POST /api/pet
  buyPet(petId: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body = petId;
    return lastValueFrom(
      this.httpClient.post(`${API_URL}/api/pets`, body,{ headers: headers }));
    // this.httpClient.get<Pet[]>('/api/pets'));
  }

  getDefaultPets(): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return lastValueFrom(
      this.httpClient.get<PetBlueprint[]>(`${API_URL}/api/pets`, { headers: headers }));
  }
}
