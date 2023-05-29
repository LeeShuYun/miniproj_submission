import { AfterViewInit, Component, Output } from '@angular/core';
import { Observable, Subscription, debounceTime, map, startWith, tap } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';
import { CharacterService } from '../_services/character.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Store } from '@ngrx/store';
import * as PlayerActions from '../_ngrx_store/player.actions';
import { PlayerstateService } from '../_shared/playerstate.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form!: FormGroup;
  jwtHelper!: JwtHelperService;
  isLoggedIn: boolean = false;

  constructor(private fb: FormBuilder,
    private router: Router,
    private authSvc: AuthService,
    private charaSvc: CharacterService,
    private store: Store,
    private playerStateSvc: PlayerstateService
  ) {
  }

  ngOnInit(): void {
    console.log("login page loading...")
    //user check
    // console.log("checking if user is logged in");
    // if (!!localStorage.getItem('jwtToken')) {
    //   this.router.navigate(['/landing', 'habits']);
    // }

    this.form = this.fb.group({
      email: this.fb.control('test@test.com', [
        Validators.required,
        Validators.email
      ]),
      password: this.fb.control('testtesttest', [
        Validators.required,
        Validators.minLength(12),
        // Validators.pattern(/^\S*$/), //no whitespace
        Validators.maxLength(30)
      ])
    });

    //checks logged in status
    if (!!localStorage.getItem('userid')) {
      this.router.navigate(['/landing', 'habits']).then(() => {
        window.location.reload()
      })
    }
  } //end nginit

  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }

  login() {
    const val = this.form.value;
    console.log("valid?", val.invalid)
    console.log("email " + val.email + " password " + val.password);
    if (val.email && val.password) {
      // this.authSvc.loginUserJWT(val.email, val.password)
      this.authSvc.loginUser(val.email, val.password)
        .then(
          (result) => {
            console.log("login result: ", result)
            // const decodedToken = this.jwtHelper.decodeToken(result); //wait do we need to decode?
            // console.log("decoded Token: ", decodedToken)
            // localStorage.setItem("jwtToken", result['jwtToken']);

            const { character, pet } = result;
            console.log("character", character);
            console.log("pet", pet);
            console.log("userid", character.userid)
            console.log("set character, pet and userid into localstorage")
            localStorage.setItem("character", JSON.stringify(character))
            localStorage.setItem("pet", JSON.stringify(pet))
            localStorage.setItem("userid", JSON.stringify(character.userid));

            console.log("LoginComponent>>>>> checking that userid is set: ", localStorage.getItem("userid"));

            const player = {
              userid: character.userid,
              characterid: character.characterid,
              health: character.health,
              coinwallet: character.coinwallet,
              currentpetid: character.currentpetid,
              image: character.imageUrl,
              isGameSectionLocked: true,
              petimage: pet.image,
              pethealing: pet.healing,
              enemyhealth: 50,
              enemyimage: 'enemy.webp'
            }
            localStorage.setItem('player', JSON.stringify(player));
            console.log("updating player info to playerstateSvc from login", player)
            this.playerStateSvc.sharedNode = player


            this.authSvc.isLoggedIn = true;
            // this.store.dispatch(PlayerActions.login());
            console.log("LoginComponent>>> User is logged in. redirecting to landing");
            this.router.navigate(['/landing', 'habits']);

          }
        ).catch(error => {
          console.error("login error: ", error)
          alert("Login failed! :( Error: " + error);
        }
        );
    }



  }



  //Google login ============================
  // @ts-ignore
  // google.accounts.id.initialize({
  //   client_id: '869245493728-jcr4ussoue4u3eu7e020s37gvee8kp05.apps.googleusercontent.com',
  //   context: "signin",
  //   // can only have either callback or login_uri NOT BOTH
  //   callback: this.handleCredentialResponse.bind(this),
  //   auto_select: false, // autoselects first google account of user to login
  //   cancel_on_tap_outside: true // cancel if user clicks outside of popup
  // })
  //   // @ts-ignore
  //   google.accounts.id.renderButton(
  //     // @ts-ignore
  //     document.getElementById("googleBtn"),
  //     { theme: "filled_blue", text: "signin_with", shape: "rectangular" }
  //   )
  //   // @ts-ignore
  //   google.accounts.id.prompt((notification: PromptMomentNotification) => { })
  // }
  // async handleCredentialResponse(response: CredentialResponse) {
  //   await this.authSvc.googleLogin(response.credential)
  //     .then((response) => {
  //       console.log(response)
  //       localStorage.setItem("jwt", response['jwt'])
  //       this._ngZone.run(() => {
  //         this.router.navigate(['/borrowed']) // send user to whatever page after logged in
  //       })
  //     })
  //     .catch(error => {
  //       console.error(error)
  //     })
}
