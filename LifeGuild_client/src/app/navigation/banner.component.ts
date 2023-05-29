import { Component, OnInit } from '@angular/core';
import { Character, PetInstance } from '../_models/player';
import { Player } from '../_shared/models';
import { PlayerstateService } from '../_shared/playerstate.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css']
})
export class BannerComponent implements OnInit {
  bgImage: String = "../../assets/Wheat_bg.png";


  //for display
  //I want to make the service public and use data directly from sharedNode but doesn't work
  // player: Player =
  //   {
  //     userid: 2,
  //     characterid: 2,
  //     coinwallet: 100,
  //     health: 0,
  //     currentpetid: 2,
  //     image: 'character.avif',
  //     isGameSectionLocked: true,
  //     petimage: 'dragon.avif',
  //     enemyhealth: 0,
  //     enemyimage: 'enemy.webp'
  //   }

  constructor(public playerStateSvc: PlayerstateService) {
    // this.playerStateSvc.onPlayerData.subscribe(
    //   (playerData) => {
    //     console.log(playerData)
    //     this.player = playerData
    //   });
  }
  ngOnInit(): void {
    console.log("banner loading.... ")
    console.log("banner player onInit: ", this.playerStateSvc.sharedNode)
  }

  print() {
    console.log(this.playerStateSvc.sharedNode);
  }
}
