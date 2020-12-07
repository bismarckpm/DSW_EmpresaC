import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitarEstudiosComponent } from './solicitar-estudios.component';

describe('SolicitarEstudiosComponent', () => {
  let component: SolicitarEstudiosComponent;
  let fixture: ComponentFixture<SolicitarEstudiosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolicitarEstudiosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SolicitarEstudiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
