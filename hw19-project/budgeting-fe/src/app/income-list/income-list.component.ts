import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { IncomeService } from '../income/income.service';
import { Observable, share } from 'rxjs';
import { GetIncomeResponse } from '../income/dto/get-income-response';
import { AsyncPipe } from '@angular/common';
import { CoinsToBasePipe } from '../helpers/coins-to-base.pipe';

@Component({
  selector: 'app-income-list',
  standalone: true,
  imports: [
    AsyncPipe,
    CoinsToBasePipe
  ],
  templateUrl: './income-list.component.html',
  styleUrl: './income-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IncomeListComponent implements OnInit {
  private readonly incomeService: IncomeService = inject(IncomeService);
  public incomeList$: Observable<GetIncomeResponse[]> | null = null;

  public ngOnInit(): void {
    this.incomeList$ = this.incomeService.getList().pipe(share());
  }
}
