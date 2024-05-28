import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateIncomeRequest } from './dto/create-income-request';
import { Observable } from 'rxjs';
import { CreateIncomeResponse } from './dto/create-income-response';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {
  private readonly http: HttpClient = inject(HttpClient);

  public create(request: CreateIncomeRequest): Observable<CreateIncomeResponse> {
    return this.http.post<CreateIncomeResponse>('api/income', request);
  }
}
