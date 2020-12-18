import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnadirEncuestadoComponent } from './anadir-encuestado.component';

describe('AnadirEncuestadoComponent', () => {
  let component: AnadirEncuestadoComponent;
  let fixture: ComponentFixture<AnadirEncuestadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnadirEncuestadoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnadirEncuestadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
