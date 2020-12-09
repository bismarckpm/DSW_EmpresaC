import { TestBed } from '@angular/core/testing';

import { AdminMarcasService } from './admin-marcas.service';

describe('AdminMarcasService', () => {
  let service: AdminMarcasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminMarcasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
