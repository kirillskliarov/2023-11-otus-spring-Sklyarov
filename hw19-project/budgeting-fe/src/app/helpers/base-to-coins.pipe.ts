import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'baseToCoins',
  standalone: true,
})
export class BaseToCoinsPipe implements PipeTransform {

  public transform(value: number): number {
    return baseToCoins(value);
  }

}

export function baseToCoins(value: number): number {
  return Math.floor(value * 100);
}
