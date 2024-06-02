import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateIncomeRequest } from './dto/create-income-request';
import { Observable } from 'rxjs';
import { CreateIncomeResponse } from './dto/create-income-response';
import { GetIncomeResponse } from './dto/get-income-response';
import { GetIncomeRequest } from './dto/get-income-request';
import { removeUndefined } from '../helpers/remove-undefined';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {
  private readonly http: HttpClient = inject(HttpClient);

  public create(request: CreateIncomeRequest): Observable<CreateIncomeResponse> {
    return this.http.post<CreateIncomeResponse>('api/income', request);
  }

  public getList(request?: GetIncomeRequest): Observable<GetIncomeResponse[]> {
    return this.http.get<GetIncomeResponse[]>('api/income', {
      params: {
        ...(request ? removeUndefined(request) : {}),
      }
    });
  }
}
