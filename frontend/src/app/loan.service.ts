import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(private readonly http: HttpClient) { }

  getLoans(): Observable<any[]> {
    return this.http.get<any[]>("http://localhost:8081/loans");
  }

}
