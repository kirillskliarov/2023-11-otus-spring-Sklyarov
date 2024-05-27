import { ChangeDetectionStrategy, Component, DestroyRef, inject } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { getSignupForm, SignupForm } from './signup-form';
import { AuthService } from '../security/auth.service';
import { SignupRequest } from '../security/dto/signup-request';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SignupComponent {
  public readonly form: FormGroup<SignupForm> = getSignupForm();
  public readonly authService: AuthService = inject(AuthService);
  public readonly destroyRef: DestroyRef = inject(DestroyRef);

  public submit(): void {
    this.form.updateValueAndValidity();
    if (this.form.valid) {
      const request: SignupRequest = {
        email: this.form.value.email ?? '',
        username: this.form.value.username ?? '',
        password: this.form.value.password ?? '',
      }

      this.authService.signin(request).pipe(
        takeUntilDestroyed(this.destroyRef),
      ).subscribe();
    }
  }

}
