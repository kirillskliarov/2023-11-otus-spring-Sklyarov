import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { IncomeService } from '../income/income.service';
import { Observable, share } from 'rxjs';
import { GetIncomeResponse } from '../income/dto/get-income-response';
import { AsyncPipe, DatePipe } from '@angular/common';
import { CoinsToBasePipe } from '../helpers/coins-to-base.pipe';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { getIncomeListRequestForm, IncomeListRequestForm, toGetIncomeRequest } from './income-list-request-form';

@Component({
  selector: 'app-income-list',
  standalone: true,
  imports: [
    AsyncPipe,
    CoinsToBasePipe,
    DatePipe,
    ReactiveFormsModule
  ],
  templateUrl: './income-list.component.html',
  styleUrl: './income-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IncomeListComponent implements OnInit {
  private readonly incomeService: IncomeService = inject(IncomeService);
  public incomeList$: Observable<GetIncomeResponse[]> | null = null;
  public readonly filterForm: FormGroup<IncomeListRequestForm> = getIncomeListRequestForm();

  public ngOnInit(): void {
    this.getList();
  }

  public getList(): void {
    const request = toGetIncomeRequest(this.filterForm.value);
    this.incomeList$ = this.incomeService.getList(request).pipe(share());
  }
}
