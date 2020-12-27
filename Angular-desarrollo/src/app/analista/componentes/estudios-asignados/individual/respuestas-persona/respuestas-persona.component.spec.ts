import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RespuestasPersonaComponent } from './respuestas-persona.component';

describe('RespuestasPersonaComponent', () => {
  let component: RespuestasPersonaComponent;
  let fixture: ComponentFixture<RespuestasPersonaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RespuestasPersonaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RespuestasPersonaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
