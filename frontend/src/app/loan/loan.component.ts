import { Component, inject } from '@angular/core';
import { LoanService } from '../loan.service';
import {MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-loan',
  imports: [MatTableModule],
  templateUrl: './loan.component.html',
  styleUrl: './loan.component.css'
})
export class LoanComponent {

  displayedColumns: string[] = ['id', 'strdata', 'addTime'];

  loansService = inject(LoanService);
  loans: any[] = [];

  ngOnInit(){
    this.loansService.getLoans().subscribe(loansRes=>{
      this.loans = loansRes;
    })
  }

}
