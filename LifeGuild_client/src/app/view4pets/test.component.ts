import { Component } from '@angular/core';
import { PetInstance } from '../_models/player';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent {
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
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
    this.testPetTrade1,
    this.testPetTrade2,
  ];
}
