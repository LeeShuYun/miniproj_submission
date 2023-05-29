import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PetInstance } from '../_models/player';
import { CharacterService } from '../_services/character.service';
import { PlayerstateService } from '../_shared/playerstate.service';
import { Player } from '../_shared/models';

@Component({
  selector: 'app-pets',
  templateUrl: './pets.component.html',
  styleUrls: ['./pets.component.css']
})
export class PetsComponent {

  testPetTrade1: PetInstance = {
    userid: 1,
    petId: 1,
    image: "R2D2.webp",
    healing: 7,
  }
  testPetTrade2: PetInstance = {
    userid: 1,
    petId: 3,
    image: "kirby.webp",
    healing: 7,
  }


  playerPet: PetInstance = {
    userid: 2,
    petId: 2,
    image: "cat.webp",
    healing: 3,
  }
  petForTradeList: PetInstance[] = [
    // this.testPetTrade1,
    // this.testPetTrade2,
    // this.testPetTrade1,
    // this.testPetTrade2
  ];
  // playerPet!: PetInstance;
  tradeablePet: PetInstance = this.testPetTrade1
  isTradeSuccess!: boolean;
  // userid!: string;
  unlockablePets!: PetInstance[];

  player = {
    userid: 0,
    characterid: 0,
    health: 0,
    coinwallet: 20,
    currentpetid: 0,
    image: 'character.avif',
    isGameSectionLocked: true,
    petimage: 'dragon.avif',
    pethealing: 2,
    enemyhealth: 0,
    enemyimage: 'enemy.webp'
  }

  constructor(
    private charaSvc: CharacterService,
    private fb: FormBuilder,
    public playerStateSvc: PlayerstateService) {
    //wire it up to the global service state

    // //@ts-ignore
    // this.playerStateSvc.onPlayerData.next(playerData);

    //@ts-ignore
    const playerData: Player = JSON.parse(localStorage.getItem('player'));
    this.player = playerData;
    console.log(playerData)
    this.playerStateSvc.onPlayerData.subscribe(
      (playerData) => {
        console.log("playerStateSvc subscription", playerData)

        this.player = playerData
      });
  }

  ngOnInit() {
    //@ts-ignore
    const playerData: Player = JSON.parse(localStorage.getItem('player'));
    this.player = playerData;
    //retrieve the marketplace pets
    this.charaSvc.getDefaultPets().then((result) => {
      console.log("getting pets from marketplacesuccessful", result)
      this.petForTradeList = result
    })
      .catch((error) => {
        console.log(error)
      }
      );

  }
  test() {
    console.log(this.playerStateSvc.sharedNode)
    console.log(this.player)
  }
  tradePet() {
    console.log("trade " + this.playerPet.image + " for " + this.tradeablePet.image)
    this.charaSvc.tradePet(this.playerPet, this.tradeablePet)
      .then(
        (result) => {
          console.log(result)

          let player = this.playerStateSvc.sharedNode
          console.log(player)
          player['coinwallet'] -= this.tradeablePet.healing
          player['currentpetid'] = this.tradeablePet.petId
          player['petimage'] = this.tradeablePet.image
          console.log(player)

          this.playerStateSvc.update.next(player)

        }

      ).catch((error) => {
        console.log(error)
      });
  }

  selectPet(index: number) {
    console.log("selected pet: ", this.petForTradeList[index])
    this.tradeablePet = this.petForTradeList[index];
  }
}
