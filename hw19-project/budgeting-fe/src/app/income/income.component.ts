import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { getIncomeForm, IncomeForm } from './income-form';
import { CreateIncomeRequest } from './dto/create-income-request';
import { IncomeService } from './income.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { IncomeListComponent } from '../income-list/income-list.component';
import { BaseToCoinsPipe } from '../helpers/base-to-coins.pipe';
import { ToISOPipe } from '../helpers/to-iso.pipe';

@Component({
  selector: 'app-income',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    IncomeListComponent,
  ],
  providers: [BaseToCoinsPipe, ToISOPipe],
  templateUrl: './income.component.html',
  styleUrl: './income.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IncomeComponent implements OnInit {
  public readonly form: FormGroup<IncomeForm> = getIncomeForm();
  public readonly incomeService: IncomeService = inject(IncomeService);
  public readonly destroyRef: DestroyRef = inject(DestroyRef);
  public readonly baseToCoinsPipe: BaseToCoinsPipe = inject(BaseToCoinsPipe);
  public readonly toISOPipe: ToISOPipe = inject(ToISOPipe);

  public ngOnInit(): void {

  }

  public create(): void {
    if (this.form.valid) {
      const amount = this.baseToCoinsPipe.transform(Number(this.form.controls.amount.value));
      const date = this.toISOPipe.transform(this.form.controls.date.value);
      const description = this.form.controls.description.value;
      const createIncomeRequest: CreateIncomeRequest = {
        amount,
        date,
        description,
      }

      this.incomeService.create(createIncomeRequest).pipe(
        takeUntilDestroyed(this.destroyRef),
      ).subscribe();
    }

  }
}
