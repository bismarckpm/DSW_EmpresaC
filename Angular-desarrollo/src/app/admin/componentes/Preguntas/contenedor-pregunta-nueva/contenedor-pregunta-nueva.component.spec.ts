import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContenedorPreguntaNuevaComponent } from './contenedor-pregunta-nueva.component';

describe('ContenedorPreguntaNuevaComponent', () => {
  let component: ContenedorPreguntaNuevaComponent;
  let fixture: ComponentFixture<ContenedorPreguntaNuevaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContenedorPreguntaNuevaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContenedorPreguntaNuevaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
