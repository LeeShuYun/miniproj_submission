import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DataService } from '../_services/data.service';
import { AuthService } from '../_services/auth.service';
import { RegisterUserDetail } from '../_models/player';
import { Router } from '@angular/router';
import Validation from '../utils/validation';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(private fb: FormBuilder,
    private authSvc: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  createForm() {
    return this.fb.group({
      firstname: this.fb.control('', [
        Validators.required,
        Validators.minLength(2),
        // Validators.pattern(/^\S*$/) //no whitespace
      ]),
      lastname: this.fb.control('', [
        Validators.required,
        Validators.minLength(2),
        // Validators.pattern(/^\S*$/) //no whitespace
      ]),
      email: this.fb.control('',
        [Validators.required, Validators.email]
      ),
      username: this.fb.control(''),
      password: this.fb.control('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(40)
      ]),
      confirmPassword: this.fb.control('', Validators.required),
      acceptTerms: this.fb.control(false, Validators.requiredTrue),
    },
      {
        validators: [Validation.match('password', 'confirmPassword')]
      }
    );
  }

  async submitForm() {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    console.log("submitted form! processing....")


    const user: RegisterUserDetail = {
      firstname: this.form.get('firstname')?.value,
      lastname: this.form.get('lastname')?.value,
      email: this.form.get('email')?.value,
      username: this.form.get('username')?.value,
      password: this.form.get('password')?.value,
      userrole: this.form.get('userrole')?.value,
      acceptterms: this.form.get('acceptterms')?.value,
      isgooglelogin: false,
    }
    console.log("submitForm()=> user info: ", JSON.stringify(this.form.value, null, 2));
    //add the user into SQL database
    await this.authSvc.registerNewUser(user)
      .then((result) => {
        console.log("registering user success!", result);

        //set so we can retrieve it for confirmation
        localStorage.setItem("email", user.email);

        console.log("userid", result['userid']);
        localStorage.setItem("userid", result['userid']);

        // this.form.reset();
        this.router.navigate(['/confirm']);
      })
      .catch((error) => {
        console.error(error);
        console.error("registering user failed! :( ", error);
        alert("Registration Failed!");
      });
  }


  //TODO - remove this after demo
  enter() {
    console.log("enter clicked")
    this.router.navigate(['/landing']);
  }
}
