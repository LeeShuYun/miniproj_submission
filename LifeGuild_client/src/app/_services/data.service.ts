import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, firstValueFrom, lastValueFrom } from 'rxjs';
import { Comment, Review } from '../models';
import { Habit, Reward, Task, User } from '../_models/player';
import { Book } from '../_models/book';
import { Character } from '../_models/player';
import { API_URL } from '../_models/constant'

@Injectable({
  providedIn: 'root'
})
export class DataService {
  //save the search
  searchTerm!: string;
  movieList: Review[] = [];
  bookList: Book[] = [];

  constructor(private httpClient: HttpClient) { }

  setSearch(searchTerm: string) {
    this.searchTerm = searchTerm;
  }

  //if syncing is like off... this is just for emergency
  // manualSyncCharacter() {
  //   this.getCharacter(this.userid)
  //     .then(
  //       (result) => this.character = JSON.parse(result)
  //     ).catch(
  //       (error) => console.error("failed to get character data")
  //     );
  // }
  //GET /api/search?query=<user's search term>
  getSearch(query: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const params = new HttpParams()
      .set("query", query);
    return lastValueFrom(
      // this.httpClient.get('http://localhost:8080/api/search', { params: params, headers: headers }));
      this.httpClient.get<Review[]>(`${API_URL}/api/book/search`, { params: params, headers: headers }));
  }
  //GET /api/book/search?query=<user's search term>
  getBooks(query: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const params = new HttpParams()
      .set("search", query);
    return lastValueFrom(
      // this.httpClient.get('http://localhost:8080/api/search', { params: params, headers: headers }));
      this.httpClient.get<Book[]>(`${API_URL}/api/book/search`, { params: params, headers: headers }));
  }

  //POST /api/task
  getAllTasks(userid: string): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const body = JSON.stringify({ "userid": userid })
    console.log("getting tasks for userid >>", body);
    return lastValueFrom(
      this.httpClient.post<Task>(`${API_URL}/api/task`, body, { headers }));
  }


}

