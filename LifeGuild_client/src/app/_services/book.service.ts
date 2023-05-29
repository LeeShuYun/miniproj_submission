import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { Book } from '../_models/book';
import { API_URL } from '../_models/constant';


@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }
  setSearch(query: String) {

  }
  //GET http://localhost:8080/api/book?search=Coraline
  getBooks(bookName: string, startIndex: number, maxResults: number): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const params = new HttpParams()
      .set("search", bookName)
      .set("startIndex", startIndex)
      .set("maxResults", maxResults);

    return firstValueFrom(
      this.httpClient.get<Book[]>(`${API_URL}/api/book`, { params: params, headers: headers }));
  }

}
