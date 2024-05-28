import { FormControl, FormGroup, Validators } from '@angular/forms';


export interface IncomeForm {
  amount: FormControl<string>;
}

export type IncomeFormValue = FormGroup<IncomeForm>['value'];

export const getIncomeForm: () => FormGroup<IncomeForm> = (): FormGroup<IncomeForm> => new FormGroup<IncomeForm>({
  amount: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
});
