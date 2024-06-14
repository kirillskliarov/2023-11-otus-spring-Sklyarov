import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { TokenService } from './token.service';
import { inject } from '@angular/core';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const tokenService = inject(TokenService);
  const token = tokenService.token();

  if (req.url.startsWith('/api/auth')) {
    return next(req);
  }

  const newReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token ?? ''}`),
  });
  return next(newReq);
}
