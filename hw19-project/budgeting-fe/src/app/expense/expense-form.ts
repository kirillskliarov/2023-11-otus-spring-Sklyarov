import { FormControl, FormGroup, Validators } from '@angular/forms';

export interface ExpenseForm {
  amount: FormControl<string>;
  description: FormControl<string>;
  date: FormControl<string>;
}

export type ExpenseFormValue = FormGroup<ExpenseForm>['value'];

export const getExpenseForm: () => FormGroup<ExpenseForm> = (): FormGroup<ExpenseForm> => new FormGroup<ExpenseForm>({
  amount: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
  description: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
  date: new FormControl<string>('', {
    nonNullable: true,
    validators: [Validators.required]
  }),
});
