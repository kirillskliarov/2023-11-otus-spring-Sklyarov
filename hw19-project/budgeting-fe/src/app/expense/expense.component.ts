import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { getExpenseForm, ExpenseForm } from './expense-form';
import { CreateExpenseRequest } from './dto/create-expense-request';
import { ExpenseService } from './expense.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ExpenseListComponent } from '../expense-list/expense-list.component';
import { BaseToCoinsPipe } from '../helpers/base-to-coins.pipe';
import { ToISOPipe } from '../helpers/to-iso.pipe';

@Component({
  selector: 'app-expense',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    ExpenseListComponent,
  ],
  providers: [BaseToCoinsPipe, ToISOPipe],
  templateUrl: './expense.component.html',
  styleUrl: './expense.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ExpenseComponent implements OnInit {
  public readonly form: FormGroup<ExpenseForm> = getExpenseForm();
  public readonly expenseService: ExpenseService = inject(ExpenseService);
  public readonly destroyRef: DestroyRef = inject(DestroyRef);
  public readonly baseToCoinsPipe: BaseToCoinsPipe = inject(BaseToCoinsPipe);
  public readonly toISOPipe: ToISOPipe = inject(ToISOPipe);

  public ngOnInit(): void {

  }

  public create(): void {
    if (this.form.valid) {
      const amount = this.baseToCoinsPipe.transform(Number(this.form.controls.amount.value));
      const date = this.toISOPipe.transform(this.form.controls.date.value);
      const createExpenseRequest: CreateExpenseRequest = {
        amount,
        date,
      }

      this.expenseService.create(createExpenseRequest).pipe(
        takeUntilDestroyed(this.destroyRef),
      ).subscribe();
    }

  }
}
