import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toISO',
  standalone: true
})
export class ToISOPipe implements PipeTransform {

  public transform(value: string): string {
    return new Date(value).toISOString();
  }

}
