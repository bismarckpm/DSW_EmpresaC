import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstudiosAsignadosComponent } from './estudios-asignados.component';

describe('EstudiosAsignadosComponent', () => {
  let component: EstudiosAsignadosComponent;
  let fixture: ComponentFixture<EstudiosAsignadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstudiosAsignadosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstudiosAsignadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
