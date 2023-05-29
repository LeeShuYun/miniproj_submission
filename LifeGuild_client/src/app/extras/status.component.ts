import { Component, Input } from '@angular/core';
// import { Character } from '../models/character';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent {
  @Input()
  // character!: Character;

  hpValue: number = 90;

  ngOnInit() {

  }
}
