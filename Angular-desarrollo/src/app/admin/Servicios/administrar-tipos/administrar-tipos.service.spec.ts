import { TestBed } from '@angular/core/testing';

import { AdministrarTiposService } from './administrar-tipos.service';

describe('AdministrarTiposService', () => {
  let service: AdministrarTiposService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrarTiposService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
