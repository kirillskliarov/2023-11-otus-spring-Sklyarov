import { FormControl, FormGroup } from '@angular/forms';
import { baseToCoins } from '../helpers/base-to-coins.pipe';
import { GetIncomeRequest } from '../income/dto/get-income-request';

export interface IncomeListRequestForm {
  amountFrom: FormControl<string>;
  amountTo: FormControl<string>;
  description: FormControl<string>;
  startDate: FormControl<string>;
  endDate: FormControl<string>;
}

export const getIncomeListRequestForm: () => FormGroup<IncomeListRequestForm> = (): FormGroup<IncomeListRequestForm> => new FormGroup<IncomeListRequestForm>({
  amountFrom: new FormControl<string>('', { nonNullable: true }),
  amountTo: new FormControl<string>('', { nonNullable: true }),
  description: new FormControl<string>('', { nonNullable: true }),
  startDate: new FormControl<string>('', { nonNullable: true }),
  endDate: new FormControl<string>('', { nonNullable: true }),
});

export const toGetIncomeRequest: (formValue: FormGroup<IncomeListRequestForm>['value']) => GetIncomeRequest = (formValue: FormGroup<IncomeListRequestForm>['value']): GetIncomeRequest => ({
  amountFrom: formValue.amountFrom ? baseToCoins(Number(formValue.amountFrom)) : undefined,
  amountTo: formValue.amountTo ? baseToCoins(Number(formValue.amountTo)) : undefined,
  description: formValue.description || undefined,
  startDate: formValue.startDate || undefined,
  endDate: formValue.endDate || undefined,
});
