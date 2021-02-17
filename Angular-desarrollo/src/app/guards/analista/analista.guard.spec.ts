import { TestBed } from '@angular/core/testing';

import { AnalistaGuard } from './analista.guard';

describe('AnalistaGuard', () => {
  let guard: AnalistaGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AnalistaGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
