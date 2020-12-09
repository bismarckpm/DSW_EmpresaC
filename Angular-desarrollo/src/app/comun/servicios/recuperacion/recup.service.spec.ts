import { TestBed } from '@angular/core/testing';

import { RecupService } from './recup.service';

describe('RecupService', () => {
  let service: RecupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
