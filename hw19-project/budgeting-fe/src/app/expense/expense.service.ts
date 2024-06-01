import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateExpenseRequest } from './dto/create-expense-request';
import { Observable } from 'rxjs';
import { CreateExpenseResponse } from './dto/create-expense-response';
import { GetExpenseResponse } from './dto/get-expense-response';
import { GetExpenseRequest } from './dto/get-expense-request';
import { removeUndefined } from '../helpers/remove-undefined';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private readonly http: HttpClient = inject(HttpClient);

  public create(request: CreateExpenseRequest): Observable<CreateExpenseResponse> {
    return this.http.post<CreateExpenseResponse>('api/expense', request);
  }

  public getList(request?: GetExpenseRequest): Observable<GetExpenseResponse[]> {
    return this.http.get<GetExpenseResponse[]>('api/expense', {
      params: {
        ...(request ? removeUndefined(request) : {}),
      }
    });
  }
}
