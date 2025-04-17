import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoanComponent } from './loan/loan.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoanComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
