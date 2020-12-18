import { TestBed } from '@angular/core/testing';

import { ConsultarEstudiosService } from './consultar-estudios.service';

describe('ConsultarEstudiosService', () => {
  let service: ConsultarEstudiosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConsultarEstudiosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
