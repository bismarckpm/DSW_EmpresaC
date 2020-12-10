import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarCategoriaComponent } from './administrar-categoria.component';

describe('AdministrarCategoriaComponent', () => {
  let component: AdministrarCategoriaComponent;
  let fixture: ComponentFixture<AdministrarCategoriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrarCategoriaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarCategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
