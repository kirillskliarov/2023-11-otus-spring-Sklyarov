import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../security/auth.service';
import { SignupRequest } from '../security/dto/signup-request';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { getSigninForm, SigninForm } from './signin-form';
import { Router } from '@angular/router';
import { SigninRequest } from '../security/dto/signin-request';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SigninComponent {
  public readonly form: FormGroup<SigninForm> = getSigninForm();
  public readonly authService: AuthService = inject(AuthService);
  public readonly destroyRef: DestroyRef = inject(DestroyRef);
  public readonly router: Router = inject(Router);

  public submit(): void {
    this.form.updateValueAndValidity();
    if (this.form.valid) {
      const request: SigninRequest = {
        username: this.form.value.username ?? '',
        password: this.form.value.password ?? '',
      }

      this.authService.signin(request).pipe(
        takeUntilDestroyed(this.destroyRef),
      ).subscribe({
        next: () => this.router.navigate(['../..']),
      });
    }
  }

}
