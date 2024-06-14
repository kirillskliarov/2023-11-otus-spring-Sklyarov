import { inject, Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SignupRequest } from './dto/signup-request';
import { SignupResponse } from './dto/signup-response';
import { SigninRequest } from './dto/signin-request';
import { SigninResponse } from './dto/signin-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly tokenService: TokenService = inject(TokenService);
  private readonly http: HttpClient = inject(HttpClient);

  public signup(request: SignupRequest): Observable<SignupResponse> {
    return this.http.post<SignupResponse>('/api/auth/sign-up', request).pipe(
      tap(response => this.tokenService.setToken(response.token)),
    );
  }

  public signin(request: SigninRequest): Observable<SigninResponse> {
    return this.http.post<SignupResponse>('/api/auth/sign-in', request).pipe(
      tap(response => this.tokenService.setToken(response.token)),
    );
  }

  public logout(): void {
    this.tokenService.setToken(null);
  }


}
