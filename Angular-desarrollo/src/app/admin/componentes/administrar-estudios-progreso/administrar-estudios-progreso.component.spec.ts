import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarEstudiosProgresoComponent } from './administrar-estudios-progreso.component';

describe('AdministrarEstudiosProgresoComponent', () => {
  let component: AdministrarEstudiosProgresoComponent;
  let fixture: ComponentFixture<AdministrarEstudiosProgresoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrarEstudiosProgresoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarEstudiosProgresoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
