import { computed, Injectable, Signal, signal, WritableSignal } from '@angular/core';

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
    const tokenValue: string | null = localStorage.getItem('token');
    this.tokenHolder.set(tokenValue);
  }

  public setToken(token: string | null): void {
    this.tokenHolder.set(token);
  }
}
