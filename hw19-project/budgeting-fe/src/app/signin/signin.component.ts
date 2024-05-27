import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, inject } from '@angular/core';
import { getSignupForm, SignupForm } from '../signup/signup-form';
import { FormGroup } from '@angular/forms';
import { AuthService } from '../security/auth.service';
import { SignupRequest } from '../security/dto/signup-request';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SigninComponent {
  public readonly authService: AuthService = inject(AuthService);
  public readonly destroyRef: DestroyRef = inject(DestroyRef);

}
