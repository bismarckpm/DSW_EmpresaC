import { TestBed } from '@angular/core/testing';

import { SolicitudEstudiosService } from './solicitud-estudios.service';

describe('SolicitudEstudiosService', () => {
  let service: SolicitudEstudiosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SolicitudEstudiosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
