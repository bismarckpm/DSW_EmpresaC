import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstudioDetalleComponent } from './estudio-detalle.component';

describe('EstudioDetalleComponent', () => {
  let component: EstudioDetalleComponent;
  let fixture: ComponentFixture<EstudioDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstudioDetalleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstudioDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
