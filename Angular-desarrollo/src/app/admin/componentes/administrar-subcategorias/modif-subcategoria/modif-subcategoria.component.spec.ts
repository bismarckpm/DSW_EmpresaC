import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifSubcategoriaComponent } from './modif-subcategoria.component';

describe('ModifSubcategoriaComponent', () => {
  let component: ModifSubcategoriaComponent;
  let fixture: ComponentFixture<ModifSubcategoriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifSubcategoriaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifSubcategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
