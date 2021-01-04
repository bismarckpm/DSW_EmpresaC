import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadosAnalistaComponent } from './resultados-analista.component';

describe('ResultadosAnalistaComponent', () => {
  let component: ResultadosAnalistaComponent;
  let fixture: ComponentFixture<ResultadosAnalistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResultadosAnalistaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultadosAnalistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
