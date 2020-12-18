import { TestBed } from '@angular/core/testing';

import { AdministrarPresentacionService } from './administrar-presentacion.service';

describe('AdministrarPresentacionService', () => {
  let service: AdministrarPresentacionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrarPresentacionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
