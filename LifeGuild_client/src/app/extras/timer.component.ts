import { Component, OnDestroy } from '@angular/core';
import { Subscribable, Subscription, timer } from 'rxjs';
import { takeWhile, tap } from 'rxjs/operators';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.css']
})
export class TimerComponent implements OnDestroy {

  time = 90000;
  hours = Math.floor(this.time / 3600);
  minutes = Math.floor((this.time / 60) - (this.hours * 60))
  seconds = this.time - (this.minutes % 60) - (this.hours * 3600)

  timer$!: Subscription;
  isTimerRunning: boolean = false;

  constructor() { }


  startTimer() {

    this.timer$ = timer(1000, 1000) //Initial delay 1 seconds and interval countdown also 1 second
      .pipe(
        takeWhile(() => this.time > 0),
        tap(() => this.time -= 1)
      )
      .subscribe(() => {
        //recalc display
        this.hours = Math.floor(this.time / 3600);
        this.minutes = Math.floor((this.time / 60) - (this.hours * 60))
        this.seconds = this.time - (this.minutes % 60) - (this.hours * 3600)
      });
  }

  resetTimer() {
    this.time = 90000;
  }

  stopTimer() {
    this.timer$.unsubscribe();
  }

  ngOnDestroy(): void {
    this.timer$.unsubscribe();
  }
}
