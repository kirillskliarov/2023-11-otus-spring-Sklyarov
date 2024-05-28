import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../security/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    RouterOutlet
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent {
  public readonly authService: AuthService = inject(AuthService);
  public readonly router: Router = inject(Router);

  public logout(): void {
    this.authService.logout();
    this.router.navigate(['./']);
  }
}
