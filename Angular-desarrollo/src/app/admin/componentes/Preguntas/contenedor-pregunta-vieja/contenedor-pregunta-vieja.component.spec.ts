import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContenedorPreguntaViejaComponent } from './contenedor-pregunta-vieja.component';

describe('ContenedorPreguntaViejaComponent', () => {
  let component: ContenedorPreguntaViejaComponent;
  let fixture: ComponentFixture<ContenedorPreguntaViejaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContenedorPreguntaViejaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContenedorPreguntaViejaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
