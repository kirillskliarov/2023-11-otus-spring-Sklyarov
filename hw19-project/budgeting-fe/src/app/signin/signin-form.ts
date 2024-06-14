import { FormControl, FormGroup, Validators } from '@angular/forms';

export interface SigninForm {
  username: FormControl<string>;
  password: FormControl<string>;
}

export type SigninFormValue = FormGroup<SigninForm>['value'];

export const getSigninForm: () => FormGroup<SigninForm> = (): FormGroup<SigninForm> => new FormGroup<SigninForm>({
  username: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
  password: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
});
