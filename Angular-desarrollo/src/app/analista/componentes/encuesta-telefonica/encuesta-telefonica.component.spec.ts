import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EncuestaTelefonicaComponent } from './encuesta-telefonica.component';

describe('EncuestaTelefonicaComponent', () => {
  let component: EncuestaTelefonicaComponent;
  let fixture: ComponentFixture<EncuestaTelefonicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EncuestaTelefonicaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EncuestaTelefonicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
