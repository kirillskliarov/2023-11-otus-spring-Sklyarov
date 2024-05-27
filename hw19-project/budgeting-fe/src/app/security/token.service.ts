import { computed, Injectable, Signal, signal, WritableSignal } from '@angular/core';

const TOKEN_KEY = 'token';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private readonly tokenHolder: WritableSignal<string | null> = signal<string | null>(null);
  public readonly token: Signal<string | null> = this.tokenHolder.asReadonly();
  public readonly isAuthenticated: Signal<boolean> = computed<boolean>((): boolean => {
    const tokenValue = this.tokenHolder();
    return tokenValue !== null;
  });

  constructor() {
    const tokenValue: string | null = localStorage.getItem(TOKEN_KEY);
    this.tokenHolder.set(tokenValue);
  }

  public setToken(token: string | null): void {
    if (token) {
      localStorage.setItem(TOKEN_KEY, token);
      this.tokenHolder.set(token);
    } else {
      localStorage.removeItem(TOKEN_KEY);
      this.tokenHolder.set(null);
    }
  }
}
