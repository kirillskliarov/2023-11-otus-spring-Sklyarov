import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { ExpenseService } from '../expense/expense.service';
import { Observable, share } from 'rxjs';
import { GetExpenseResponse } from '../expense/dto/get-expense-response';
import { AsyncPipe, DatePipe } from '@angular/common';
import { CoinsToBasePipe } from '../helpers/coins-to-base.pipe';

@Component({
  selector: 'app-expense-list',
  standalone: true,
  imports: [
    AsyncPipe,
    CoinsToBasePipe,
    DatePipe
  ],
  templateUrl: './expense-list.component.html',
  styleUrl: './expense-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ExpenseListComponent implements OnInit {
  private readonly expenseService: ExpenseService = inject(ExpenseService);
  public expenseList$: Observable<GetExpenseResponse[]> | null = null;

  public ngOnInit(): void {
    this.expenseList$ = this.expenseService.getList().pipe(share());
  }
}
