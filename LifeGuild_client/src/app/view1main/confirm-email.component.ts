import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, map, startWith, tap } from 'rxjs';
import { AuthService } from '../_services/auth.service';
import { CharacterService } from '../_services/character.service';

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.css']
})
export class ConfirmEmailComponent implements OnInit {

  form!: FormGroup;
  isFormInvalid$!: Observable<boolean>;

  constructor(private fb: FormBuilder,
    private router: Router,
    private authSvc: AuthService,
    private charaSvc: CharacterService
  ) {
  }

  get getIsFormInvalid$(): Observable<boolean> {
    return this.form.statusChanges.pipe(
      startWith('INVALID'),
      map((v) => 'INVALID' == v)
    );
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      code: this.fb.control('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6)
      ])
    });
    this.isFormInvalid$ = this.getIsFormInvalid$;
  }

  confirmEmail() {
    const val = this.form.value;

    if (!!localStorage.getItem("email")) {
      const email = localStorage.getItem("email");
      //@ts-ignore
      this.authSvc.confirmEmail(email, val.code)
        .then((result) => {
          console.log("email confirmed!", result);
          localStorage.setItem("jwt", result.jwt);
          this.router.navigate(['/landing', 'habits']);
        })
        .catch(error => {
          console.error("confirmation error: ", error)
          alert("Confirmation failed! :(");
        })
    }
  }
}
