import { Injectable } from '@angular/core';
import { Character } from '../_models/player'
import { Player } from './models';
import { Subject, filter, map } from 'rxjs';
import { CharacterService } from '../_services/character.service';

/**
 * this is used in 3 components
 * HabitsComponent - to pull the coins earned and spent
 * NavbarComponent - to display the amount of coins
 * PetComponent - to change the current pet of the player when trading
 */
@Injectable({
  providedIn: 'root'
})
export class PlayerstateService {
  update = new Subject<Player>;
  onPlayerData = new Subject<Player>;

  // Player - has obviously fake values
  //so we know it hasn't updated on login
  sharedNode: Player = {
    userid: 0,
    characterid: 0,
    health: 0,
    coinwallet: 20,
    currentpetid: 0,
    image: 'character.avif',
    isGameSectionLocked: true,
    petimage: 'dragon.avif',
    pethealing: 0,
    enemyhealth: 0,
    enemyimage: 'enemy.webp'
  }

  updateSharedNode(updatedData: any) {
    this.sharedNode = updatedData;
  }



  //save the new state then boomerang the data out
  //any updates should come from injected execute
  constructor(private characterSvc: CharacterService) {
    //check local storage for data
    // services reconstruct when we refresh... why
    if (!!localStorage.getItem('player')) {
      console.log('playerstateSvc: retrieving player data...')

      //@ts-ignore
      const player = JSON.parse(localStorage.getItem('player'));

      console.log("localstorage player", player)
      this.sharedNode = player;
      this.onPlayerData.next(player)
    }

    this.update.subscribe((playerData) => {
      console.log("playerstateService>>> UPDATING playerData", playerData)

      //update database
      this.characterSvc.updatePlayer(playerData)
        .then((result) => {
          console.log(result)
          //update this node for display whenever someone sends it
          this.updateSharedNode(playerData)
          localStorage.setItem('player', JSON.stringify(playerData))
          
          //send data to rest of the app - optional??
          //TODO - needs refactor of other components to remove
          this.onPlayerData.next(playerData)
        })
        .catch((error) => {
          console.log(error)

        });



    });

  }
}
