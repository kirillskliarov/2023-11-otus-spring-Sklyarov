import { inject, Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SignupRequest } from './dto/signup-request';
import { SignupResponse } from './dto/signup-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly tokenService: TokenService = inject(TokenService);
  private readonly http: HttpClient = inject(HttpClient);

  constructor() { }

  public signin(request: SignupRequest): Observable<SignupResponse> {
    return this.http.post<SignupResponse>('/api/auth/sign-up', request).pipe(
      tap(response => this.tokenService.setToken(response.token)),
    );
  }


}
