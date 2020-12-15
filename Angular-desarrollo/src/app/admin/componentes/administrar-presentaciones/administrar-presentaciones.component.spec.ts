import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarPresentacionesComponent } from './administrar-presentaciones.component';

describe('AdministrarPresentacionesComponent', () => {
  let component: AdministrarPresentacionesComponent;
  let fixture: ComponentFixture<AdministrarPresentacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrarPresentacionesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarPresentacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
