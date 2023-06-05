import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../_services/data.service';
import { Character } from '../_models/player';
import { User } from '../_models/player';
import { CharacterService } from '../_services/character.service';
import { AuthService } from '../_services/auth.service';
import { Observable } from 'rxjs';
import { select, Store } from '@ngrx/store';
import * as playerActions from '../_ngrx_store/player.actions';
import { AppState } from '../_ngrx_store/gameunlock';
import { isLoggedIn, isLoggedOut } from '../_ngrx_store/gameunlock_final/game.selectors';
import { isLocked, isUnlocked } from '../_ngrx_store/gameunlock_final/game.actions'
import { PlayerstateService } from '../_shared/playerstate.service';
import { Player } from '../_shared/models';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  // coinwallet$!: Observable<number>;

  // isUnlocked$!: Observable<boolean>;
  // isLocked$!: Observable<boolean>;

  node = {};

  player: Player =
    {
      userid: "98c2ba96",
      characterid: 2,
      coinwallet: 100,
      health: 80,
      currentpetid: 2,
      image: 'character.avif',
      isGameSectionLocked: true,
      petimage: 'dragon.avif',
      pethealing: 7,
      enemyhealth: 40,
      enemyimage: 'enemy.webp'
    }

  constructor(private router: Router,
    public sharedPlayerSvc: PlayerstateService,
    // private store: Store<AppState>
  ) {
    this.player = this.sharedPlayerSvc.sharedNode
  }


  get sharedNode() {
    return this.sharedPlayerSvc.sharedNode;
  }

  ngOnInit(): void {
    console.log("navbar loading...")

    console.log("loading the character from sharedNode")
    this.node = this.sharedPlayerSvc.sharedNode;

  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/']);
  }


  test() {

  }


  //future idea for ambiance?
  // playAmbientSound() {
  //   let audio = new Audio();
  //   audio.src = "../assets/sound/super-mario-coin-sound.mp3";
  //   audio.load();
  //   audio.play();
  // }

  // onVolumeClick() {
  //   if (this.volumeImg == "volume.png") {
  //     this.volumeImg = "mute.png";
  //   } else {
  //     this.volumeImg = "volume.png"
  //     this.playAmbientSound();
  //   }
  // }
}
