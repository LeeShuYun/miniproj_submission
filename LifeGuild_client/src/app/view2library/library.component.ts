import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../_services/data.service';
import { Observable, debounceTime, firstValueFrom, map, startWith, tap } from 'rxjs';


@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent {
  form!: FormGroup;
  img: string = '../../assets/search.png';
  // printtest!: Observable<any>;

  constructor(private fb: FormBuilder,
    private router: Router,
    private aRoute: ActivatedRoute,
    private dataSvc: DataService
  ) {
  }
  ngOnInit(): void {
    this.form = this.createForm();
  }

  submitForm() {
    const query = this.form.get('search')?.value;
    this.dataSvc.setSearch(query)
    console.log("view0 queryString>>> ", query);
    this.form.reset();

    //redirect to /search?query=<userquery>
    this.router.navigate(['landing', 'search'], {
      queryParams: { query }
    });
  }


  createForm(): FormGroup {
    return this.fb.group({
      search: this.fb.control('', [
        Validators.required,
        Validators.minLength(2),
        // nonWhiteSpace,
        Validators.pattern(/^\S*$/) //no whitespace
      ])
    });
  }

  isControlInvalid(ctrlName: string) {
    const ctrl = this.form.get(ctrlName) as FormControl;
    return ctrl.invalid && !ctrl.pristine;
  }

}
