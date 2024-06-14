import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthComponent } from './auth/auth.component';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { IncomeComponent } from './income/income.component';
import { AppComponent } from './app.component';
import { isAuthenticatedGuard } from './security/is-authenticated-guard';
import { ExpenseComponent } from './expense/expense.component';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'signin',
      },
      {
        path: 'signup',
        component: SignupComponent,
      },
      {
        path: 'signin',
        component: SigninComponent,
      }
    ],
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [isAuthenticatedGuard()],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'income',
      },
      {
        path: 'income',
        component: IncomeComponent,
      },
      {
        path: 'expense',
        component: ExpenseComponent,
      }
    ],
  },

];
