import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'coinsToBase',
  standalone: true
})
export class CoinsToBasePipe implements PipeTransform {

  public transform(value: number): number {
    return value / 100;
  }

}
