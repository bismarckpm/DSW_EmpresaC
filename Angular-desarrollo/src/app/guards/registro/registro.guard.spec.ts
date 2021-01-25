import { TestBed } from '@angular/core/testing';

import { RegistroGuard } from './registro.guard';

describe('RegistroGuard', () => {
  let guard: RegistroGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RegistroGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
