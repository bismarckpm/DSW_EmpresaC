import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaEstudioProgresoComponent } from './tarjeta-estudio-progreso.component';

describe('TarjetaEstudioProgresoComponent', () => {
  let component: TarjetaEstudioProgresoComponent;
  let fixture: ComponentFixture<TarjetaEstudioProgresoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaEstudioProgresoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaEstudioProgresoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
