import { Component, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Review } from '../models';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subject, Subscription, firstValueFrom } from 'rxjs';
import { DataService } from '../_services/data.service';
import { BookService } from '../_services/book.service';
import { Book } from '../_models/book';

@Component({
  selector: 'app-searchresult',
  templateUrl: './searchresult.component.html',
  styleUrls: ['./searchresult.component.css']
})
export class SearchresultComponent implements OnInit, OnDestroy {
  query = "";
  queryParams$!: Subscription;

  bookList: Book[] = [];
  isSearchResultReady: boolean = false;

  placeholderImg: string = "/assets/placeholder.jpg";

  @Input()
  startIndex = 0;

  @Output()
  valueChange = new Subject<number>;

  constructor(
    private activatedRoute: ActivatedRoute,
    private dataSvc: DataService,
    private bookSvc: BookService,
    private router: Router) {
  }


  ngOnInit(): void {
    this.isSearchResultReady = false;

    console.log("loading book list");
    //grab from url
    this.query = this.activatedRoute.snapshot.queryParams["query"];
    localStorage.setItem("query", this.query)

    console.log("searchresultComponent query>>>", this.query)

    //api/book?search=Coraline&startIndex=0&maxResults=20
    this.bookSvc.getBooks(this.query, this.startIndex, 30)
      .then(books => {
        this.bookList = books;
        console.log("search result>>> ", this.bookList)
        this.isSearchResultReady = true;
        localStorage.setItem("bookList", JSON.stringify(books));
      });
  }

  paginateBooks(startIndexIncr: number) {
    if (this.startIndex + startIndexIncr >= 0) {
      this.startIndex += startIndexIncr
      this.valueChange.next(this.startIndex)
      // window.location.reload()
      this.bookSvc.getBooks(this.query, this.startIndex, 30)
        .then(books => {
          this.bookList = books;
          console.log("search result>>> ", this.bookList)
          this.isSearchResultReady = true;
          localStorage.setItem("bookList", JSON.stringify(books));
        });
    } else {
      console.log("start index can't go lower than 0")
    }
  }

  ngOnDestroy(): void {
    // this.queryParams$.unsubscribe();
    // this.movieSub.unsubscribe();
    console.log("destroyed sub");
  }

}
