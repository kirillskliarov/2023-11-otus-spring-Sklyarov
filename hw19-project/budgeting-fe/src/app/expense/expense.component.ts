import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { getExpenseForm, ExpenseForm, getExpenseCategoryForm, ExpenseCategoryForm } from './expense-form';
import { CreateExpenseRequest } from './dto/create-expense-request';
import { ExpenseService } from './expense.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ExpenseListComponent } from '../expense-list/expense-list.component';
import { BaseToCoinsPipe } from '../helpers/base-to-coins.pipe';
import { ToISOPipe } from '../helpers/to-iso.pipe';
import { CreateExpenseCategoryRequest } from './dto/create-expense-category-request';
import { Observable, share } from 'rxjs';
import { GetExpenseCategoryResponse } from './dto/get-expense-category-response';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-expense',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    ExpenseListComponent,
    AsyncPipe,
  ],
  providers: [BaseToCoinsPipe, ToISOPipe],
  templateUrl: './expense.component.html',
  styleUrl: './expense.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ExpenseComponent implements OnInit {
  public readonly form: FormGroup<ExpenseForm> = getExpenseForm();
  public readonly expenseCategoryForm: FormGroup<ExpenseCategoryForm> = getExpenseCategoryForm();
  public readonly expenseService: ExpenseService = inject(ExpenseService);
  public readonly destroyRef: DestroyRef = inject(DestroyRef);
  public readonly baseToCoinsPipe: BaseToCoinsPipe = inject(BaseToCoinsPipe);
  public readonly toISOPipe: ToISOPipe = inject(ToISOPipe);
  public expenseCategoryList$: Observable<GetExpenseCategoryResponse[]> = this.expenseService.getExpenseCategoryList().pipe(share());

  public ngOnInit(): void {

  }

  public create(): void {
    if (this.form.valid) {
      const amount = this.baseToCoinsPipe.transform(Number(this.form.controls.amount.value));
      const date = this.toISOPipe.transform(this.form.controls.date.value);
      const description = this.form.controls.description.value ?? '';
      const categoryId = Number(this.form.controls.categoryId.value);
      const createExpenseRequest: CreateExpenseRequest = {
        amount,
        date,
        description,
        categoryId,
      }

      this.expenseService.create(createExpenseRequest).pipe(
        takeUntilDestroyed(this.destroyRef),
      ).subscribe();
    }
  }

  public createExpenseCategory(): void {
    if (this.expenseCategoryForm.valid) {
      const description = this.expenseCategoryForm.controls.description.value ?? '';
      const createExpenseCategoryRequest: CreateExpenseCategoryRequest = {
        description,
      }

      this.expenseService.createExpenseCategory(createExpenseCategoryRequest).pipe(
        takeUntilDestroyed(this.destroyRef),
      ).subscribe();
    }
  }

}
