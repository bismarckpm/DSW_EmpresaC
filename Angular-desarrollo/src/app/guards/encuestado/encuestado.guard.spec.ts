import { TestBed } from '@angular/core/testing';

import { EncuestadoGuard } from './encuestado.guard';

describe('EncuestadoGuard', () => {
  let guard: EncuestadoGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(EncuestadoGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
