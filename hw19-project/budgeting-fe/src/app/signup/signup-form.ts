import { FormControl, FormGroup, Validators } from '@angular/forms';

export interface SignupForm {
  email: FormControl<string>;
  username: FormControl<string>;
  password: FormControl<string>;
}

export type SigninFormValue = FormGroup<SignupForm>['value'];

export const getSignupForm: () => FormGroup<SignupForm> = (): FormGroup<SignupForm> => new FormGroup<SignupForm>({
  email: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
  username: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
  password: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
});
