import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { loanGuard } from './loan.guard';

describe('loanGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => loanGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
