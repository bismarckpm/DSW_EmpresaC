import { TestBed } from '@angular/core/testing';

import { ConsultaEstudiosService } from './consulta-estudios.service';

describe('ConsultaEstudiosService', () => {
  let service: ConsultaEstudiosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConsultaEstudiosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
