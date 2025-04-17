import { Routes } from '@angular/router';
import { LoanComponent } from './loan/loan.component';
import { loanGuard } from './loan.guard';

export const routes: Routes = [
    {
        path: '',
        component: LoanComponent,
        canActivate: [loanGuard]
    }
];
