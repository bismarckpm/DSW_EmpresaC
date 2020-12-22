import { TestBed } from '@angular/core/testing';

import { AdministrarSubcategoriasService } from './administrar-subcategorias.service';

describe('AdministrarSubcategoriasService', () => {
  let service: AdministrarSubcategoriasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrarSubcategoriasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
