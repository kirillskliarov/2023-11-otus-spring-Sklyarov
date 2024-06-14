import { CanActivateFn, GuardResult, MaybeAsync, RedirectCommand, Router } from '@angular/router';
import { TokenService } from './token.service';
import { inject } from '@angular/core';

export const isAuthenticatedGuard: () => CanActivateFn = (): CanActivateFn => {
  return (): MaybeAsync<GuardResult> => {
    const tokenService = inject(TokenService);
    const isAuthenticated = tokenService.isAuthenticated();

    return isAuthenticated || new RedirectCommand(inject(Router).parseUrl('/auth'));
  };
}
