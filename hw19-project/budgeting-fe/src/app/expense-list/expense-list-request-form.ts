import { FormControl, FormGroup } from '@angular/forms';
import { GetExpenseRequest } from '../expense/dto/get-expense-request';
import { baseToCoins } from '../helpers/base-to-coins.pipe';

export interface ExpenseListRequestForm {
  amountFrom: FormControl<string>;
  amountTo: FormControl<string>;
  description: FormControl<string>;
  startDate: FormControl<string>;
  endDate: FormControl<string>;
  categoryId: FormControl<string>;
}

export const getExpenseListRequestForm: () => FormGroup<ExpenseListRequestForm> = (): FormGroup<ExpenseListRequestForm> => new FormGroup<ExpenseListRequestForm>({
  amountFrom: new FormControl<string>('', { nonNullable: true }),
  amountTo: new FormControl<string>('', { nonNullable: true }),
  description: new FormControl<string>('', { nonNullable: true }),
  startDate: new FormControl<string>('', { nonNullable: true }),
  endDate: new FormControl<string>('', { nonNullable: true }),
  categoryId: new FormControl<string>('', { nonNullable: true }),
});

export const toGetExpenseRequest: (formValue: FormGroup<ExpenseListRequestForm>['value']) => GetExpenseRequest = (formValue: FormGroup<ExpenseListRequestForm>['value']): GetExpenseRequest => ({
  amountFrom: formValue.amountFrom ? baseToCoins(Number(formValue.amountFrom)) : undefined,
  amountTo: formValue.amountTo ? baseToCoins(Number(formValue.amountTo)) : undefined,
  description: formValue.description || undefined,
  startDate: formValue.startDate || undefined,
  endDate: formValue.endDate || undefined,
  categoryId: formValue.categoryId ? Number(formValue.categoryId) : undefined
});
