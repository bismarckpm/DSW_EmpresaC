import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnadirClienteComponent } from './anadir-cliente.component';

describe('AnadirClienteComponent', () => {
  let component: AnadirClienteComponent;
  let fixture: ComponentFixture<AnadirClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnadirClienteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnadirClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
