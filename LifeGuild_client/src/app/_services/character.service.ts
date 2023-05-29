import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, firstValueFrom, lastValueFrom } from 'rxjs';
import { Comment, Review } from '../models';
import { Habit, Reward, Task, User } from '../_models/player';
import { Character, PetInstance } from '../_models/player';
import { API_URL } from '../_models/constant'
import { Player } from '../_shared/models';
@Injectable({
  providedIn: 'root'
})
export class CharacterService {


  character!: Character;
  playerPet!: PetInstance;
  //default character in mysql is 2

  constructor(private httpClient: HttpClient) { }

  //if syncing is like off... this is just for emergency
  // manualSyncCharacter() {
  //   this.getCharacter(this.userid)
  //     .then(
  //       (result) => this.character = JSON.parse(result)
  //     ).catch(
  //       (error) => console.error("manualSyncCharacter>> failed to get character data", error)
  //     );
  // }

  getCharacter(userid: string) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body = { "userid": userid }
    return lastValueFrom(
      this.httpClient.post<Character>(`${API_URL}/api/player/character`, body, { headers }));
  }

  //GET /api/player/  grabs current character details, pets with userid
  // getSession(userid: number): Promise<any> {
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  //   const body = userid
  //   console.log("getting character for userid >>", userid);
  //   return lastValueFrom(
  //     this.httpClient.post<Character>("/api/player", body, { headers }));
  // }

  //POST /api/task
  getAllTasks(userid: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body = JSON.stringify({ "userid": userid })
    console.log("getting tasks for userid >>", body);
    return lastValueFrom(
      this.httpClient.post<Task>(`${API_URL}/api/task`, body, { headers }));
  }

  // PUT /api/pet. returns true for transaction confirmed
  tradePet(playerPet: PetInstance, tradeablePet: PetInstance): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const transactionDetails = {
      fromUserId: tradeablePet.userid,
      toUserId: playerPet.userid,
      petId: tradeablePet.petId,
      amount: tradeablePet.healing
    }
    const body = JSON.stringify(transactionDetails);
    console.log("sending tradePet request /api/player/tradepet", body)
    return firstValueFrom(
      this.httpClient.put<boolean>(`${API_URL}/api/player/tradepet`, body, { headers: headers }));
  }

  // GET /api/pet. grabs the set of pet blueprints.
  getDefaultPets(): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return firstValueFrom(
      // this.httpClient.get('http://localhost:8080/api/pets', { headers: headers }));
      this.httpClient.get<boolean>(`${API_URL}/api/player/pets`, { headers: headers }));
  }


  //TO TEST
  updatePlayer(playerData: Player) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body = playerData
    console.log("updating playerData to DB >>", body);
    return lastValueFrom(
      this.httpClient.post<Player>(`${API_URL}/api/player/update`, body, { headers }));
  }

  // startUserSession(result: any) {
  //   console.log("userSession")
  //   console.log("startUserSession", result);
  // }

}

