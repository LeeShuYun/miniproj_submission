import { Injectable } from '@angular/core';
import { EventData } from './event.class';
import { Subject, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';

//pubsub system
//listen and emit events frm independent components
@Injectable({
  providedIn: 'root'
})
export class EventBusService {
  private subject$ = new Subject<EventData>();

  constructor() { }

  //emit event to our bus
  emit(event: EventData) {
    this.subject$.next(event);
  }

  //catches listeners with eventName, then passes the Eventdata.value to action
  on(eventName: string, action: any): Subscription {
    return this.subject$.pipe(
      //filter for a listener with eventName
      filter((e: EventData) => e.name === eventName),
      //take out "value" property of each eventData and pass "value" to callback func "action"
      map((e: EventData) => e["value"])).subscribe(action);
  }
}
