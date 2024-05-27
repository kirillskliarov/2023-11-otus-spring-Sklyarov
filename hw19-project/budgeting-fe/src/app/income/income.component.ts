import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-income',
  standalone: true,
  imports: [],
  templateUrl: './income.component.html',
  styleUrl: './income.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IncomeComponent {

}
