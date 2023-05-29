import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-game-notif',
  templateUrl: './game-notif.component.html',
  styleUrls: ['./game-notif.component.css']
})
export class GameNotifComponent {

  n = 0;

  //a list to receive notif
  @Input()
  notificationsList: string[] = [];

  addNotification() {
    if (this.n > (this.notificationsList.length - 1)) return;

    //translate to left
    // this.notifications[this.n].style.transform = 'translateX(0)';

    //translates the next few notifs to beside the first
    // for (var i = (this.n - 1); i > -1; i--) {
    //   this.notifications[i].style.transform = 'translateX(' + ((n - i) * (1 + 120 + 1 + 4)) + 'px)';
    // }

    this.n++;
  }

}
