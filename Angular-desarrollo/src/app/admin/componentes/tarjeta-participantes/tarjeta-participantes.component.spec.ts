import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaParticipantesComponent } from './tarjeta-participantes.component';

describe('TarjetaParticipantesComponent', () => {
  let component: TarjetaParticipantesComponent;
  let fixture: ComponentFixture<TarjetaParticipantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaParticipantesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaParticipantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
