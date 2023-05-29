import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from './_services/auth.service';
import { EventBusService } from './_shared/event-bus.service';
import { CharacterService } from './_services/character.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = "LifeGuild | The Productivity Game";

  private roles: string[] = [];
  // isLoggedIn = false;
  // showAdminBoard = false;
  // showModeratorBoard = false;
  // username?: string;

  // eventBusSub?: Subscription;

  constructor(
    private charaSvc: CharacterService,
    private authService: AuthService,
    private eventBusService: EventBusService
  ) { }

  ngOnInit(): void {
    // this.isLoggedIn = this.charaSvc.isLoggedIn();

    // if (this.isLoggedIn) {
    // const user = this.authService.getUser();
    // this.roles = user.roles;

    // this.showAdminBoard = this.roles.includes('ADMIN');
    // this.showModeratorBoard = this.roles.includes('MODERATOR');

    // this.username = user.username;
    // }

    // this.eventBusSub = this.eventBusService.on('logout', () => {
    // this.logout();
    // });
  }

  // logout(): void {
  //   this.authService.logout().subscribe({
  //     next: (res: any) => {
  //       console.log(res);
  //       this.storageService.clean();

  //       window.location.reload();
  //     },
  //     error: (err: any) => {
  //       console.log(err);
  //     }
  //   });
  // }
}
