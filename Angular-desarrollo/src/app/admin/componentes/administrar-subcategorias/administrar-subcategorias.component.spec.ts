import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarSubcategoriasComponent } from './administrar-subcategorias.component';

describe('AdministrarSubcategoriasComponent', () => {
  let component: AdministrarSubcategoriasComponent;
  let fixture: ComponentFixture<AdministrarSubcategoriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrarSubcategoriasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarSubcategoriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
