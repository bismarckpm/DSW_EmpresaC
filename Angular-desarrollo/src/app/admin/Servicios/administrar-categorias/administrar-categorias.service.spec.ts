import { TestBed } from '@angular/core/testing';

import { AdministrarCategoriasService } from './administrar-categorias.service';

describe('AdministrarCategoriasService', () => {
  let service: AdministrarCategoriasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrarCategoriasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
