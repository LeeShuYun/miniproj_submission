import { Component, OnInit } from '@angular/core';
import { Character } from '../_models/player';
import { DataService } from '../_services/data.service';
import { CharacterService } from '../_services/character.service';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent {

  constructor(public charaSvc: CharacterService) {
  }


}
