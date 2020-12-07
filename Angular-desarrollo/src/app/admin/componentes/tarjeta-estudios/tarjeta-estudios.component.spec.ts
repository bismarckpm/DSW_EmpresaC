import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaEstudiosComponent } from './tarjeta-estudios.component';

describe('TarjetaEstudiosComponent', () => {
  let component: TarjetaEstudiosComponent;
  let fixture: ComponentFixture<TarjetaEstudiosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaEstudiosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaEstudiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
