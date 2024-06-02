import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateExpenseRequest } from './dto/create-expense-request';
import { Observable } from 'rxjs';
import { CreateExpenseResponse } from './dto/create-expense-response';
import { GetExpenseResponse } from './dto/get-expense-response';
import { GetExpenseRequest } from './dto/get-expense-request';
import { removeUndefined } from '../helpers/remove-undefined';
import { CreateExpenseCategoryRequest } from './dto/create-expense-category-request';
import { CreateExpenseCategoryResponse } from './dto/create-expense-category-response';
import { GetExpenseCategoryResponse } from './dto/get-expense-category-response';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private readonly http: HttpClient = inject(HttpClient);

  public create(request: CreateExpenseRequest): Observable<CreateExpenseResponse> {
    return this.http.post<CreateExpenseResponse>('api/expense', request);
  }

  public createExpenseCategory(request: CreateExpenseCategoryRequest): Observable<CreateExpenseCategoryResponse> {
    return this.http.post<CreateExpenseCategoryResponse>('api/expense-category', request);
  }

  public getList(request?: GetExpenseRequest): Observable<GetExpenseResponse[]> {
    return this.http.get<GetExpenseResponse[]>('api/expense', {
      params: {
        ...(request ? removeUndefined(request) : {}),
      }
    });
  }

  public getExpenseCategoryList(): Observable<GetExpenseCategoryResponse[]> {
    return this.http.get<GetExpenseCategoryResponse[]>('api/expense-category');
  }
}
