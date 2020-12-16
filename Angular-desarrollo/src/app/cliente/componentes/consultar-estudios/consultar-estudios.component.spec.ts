import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultarEstudiosComponent } from './consultar-estudios.component';

describe('ConsultarEstudiosComponent', () => {
  let component: ConsultarEstudiosComponent;
  let fixture: ComponentFixture<ConsultarEstudiosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultarEstudiosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultarEstudiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
