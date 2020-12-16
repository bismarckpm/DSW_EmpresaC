import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantesEstudioComponent } from './participantes-estudio.component';

describe('ParticipantesEstudioComponent', () => {
  let component: ParticipantesEstudioComponent;
  let fixture: ComponentFixture<ParticipantesEstudioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParticipantesEstudioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantesEstudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
