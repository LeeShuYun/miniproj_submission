import { AbstractControl, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

//additional validation to check for password matching when registering
export default class Validation {
  static match(controlName: string, checkControlName: string): ValidatorFn {
    return (controls: AbstractControl) => {
      //get the password field values
      const control = controls.get(controlName);
      const checkControl = controls.get(checkControlName);

      //yes the two passwords match
      if (checkControl?.errors && !checkControl.errors['matching']) {
        return null;
      }
      //if the two passwords don't match
      if (control?.value !== checkControl?.value) {
        controls.get(checkControlName)?.setErrors({ matching: true });
        return { matching: true };
      } else {
        return null;
      }
    };
  }

  static futureDateValidator(): ValidatorFn {
    // null if there's no problems
    return (control: AbstractControl): ValidationErrors | null => {
      const selectedDate = new Date(control.value);
      const currentDate = new Date();
      //if selected Date is future, return null or set error
      return selectedDate <= currentDate ? null : { invalidDate: true };
    };
  }
}


