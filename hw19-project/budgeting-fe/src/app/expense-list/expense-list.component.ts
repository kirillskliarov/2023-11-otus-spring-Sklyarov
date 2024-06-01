import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { ExpenseService } from '../expense/expense.service';
import { Observable, share } from 'rxjs';
import { GetExpenseResponse } from '../expense/dto/get-expense-response';
import { AsyncPipe, DatePipe } from '@angular/common';
import { CoinsToBasePipe } from '../helpers/coins-to-base.pipe';
import { ExpenseListRequestForm, getExpenseListRequestForm, toGetExpenseRequest } from './expense-list-request-form';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [
    AsyncPipe,
    CoinsToBasePipe,
    DatePipe,
    ReactiveFormsModule
  ],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ExpenseListComponent implements OnInit {
  private readonly expenseService: ExpenseService = inject(ExpenseService);
  public expenseList$: Observable<GetExpenseResponse[]> | null = null;
  public readonly filterForm: FormGroup<ExpenseListRequestForm> = getExpenseListRequestForm();

  public ngOnInit(): void {
    this.getList();
  }

  public getList(): void {
    const request = toGetExpenseRequest(this.filterForm.value);
    this.expenseList$ = this.expenseService.getList(request).pipe(share());
  }
}
