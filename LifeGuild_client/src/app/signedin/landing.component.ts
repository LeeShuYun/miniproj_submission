import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CharacterService } from '../_services/character.service';
import { Character, PetInstance, PlayerSession } from '../_models/player';
import { Store } from '@ngrx/store';
import * as PlayerActions from "../_ngrx_store/player.actions"
import { Observable } from 'rxjs';
import { selectCharacterFromStore } from '../_ngrx_store/player.reducer';
import { HabitsComponent } from '../view3habits/habits.component';
import { NavbarComponent } from '../navigation/navbar.component';
import { PlayerstateService } from '../_shared/playerstate.service';
@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {
  userid!: string;
  // contentPrinted: boolean = false;

  character$: Observable<Character> = this.store.select('character');
  pet$: Observable<PetInstance> = this.store.select('pet');
  selectedPetId: Observable<number> = this.store.select('selectedPetId');

  character!: Character;

  node={};

  constructor(
    // private changeDetector: ChangeDetectorRef,
    private charaSvc: CharacterService,
    public sharedPlayerSvc: PlayerstateService,
    private store: Store<{ character: Character, pet: PetInstance, selectedPetId: number }>
  ) {
     this.node = this.getSharedNode;
  }
  ngOnInit(): void {
    console.log("landing loading....")
    //ngrx
    // this.store.dispatch(PlayerActions.enterLandingPage());

    //at point of successfully registering their localstorage or login should have received userid
    //@ts-ignore
    this.userid = localStorage.getItem("userid");
  }

  get getSharedNode() {
    return this.sharedPlayerSvc.sharedNode;
  }


  
  //onOutletLoaded($event)
  //passing data through the router outlet
  // onOutletLoaded(component: NavbarComponent | HabitsComponent) {
  //   if (component instanceof NavbarComponent) {
  //     //pass up character to nav
  //     component.character = this.character;
  //   }
  //   if (component instanceof HabitsComponent) {
  //     component.character = this.character;
  //   }
  // }

}


  // test() {
  //   this.charaSvc.getCharacter(this.userid)
  //     .then((result) => {
  //       console.log(result)
  //     }).catch((error) => {
  //       console.error(error);
  //     })
  // }
  // onContentPrinted() {
  //   this.contentPrinted = true;
  //   this.changeDetector.detectChanges();
  // }



