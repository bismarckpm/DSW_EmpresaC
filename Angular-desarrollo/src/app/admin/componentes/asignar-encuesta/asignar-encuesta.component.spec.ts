import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignarEncuestaComponent } from './asignar-encuesta.component';

describe('AsignarEncuestaComponent', () => {
  let component: AsignarEncuestaComponent;
  let fixture: ComponentFixture<AsignarEncuestaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsignarEncuestaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsignarEncuestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
