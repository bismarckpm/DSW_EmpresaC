import { TestBed } from '@angular/core/testing';

import { AdministrarUsuariosService } from './administrar-usuarios.service';

describe('AdministrarUsuariosService', () => {
  let service: AdministrarUsuariosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrarUsuariosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
