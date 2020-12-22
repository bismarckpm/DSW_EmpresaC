import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarPresentacionComponent } from './modificar-presentacion.component';

describe('ModificarPresentacionComponent', () => {
  let component: ModificarPresentacionComponent;
  let fixture: ComponentFixture<ModificarPresentacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModificarPresentacionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificarPresentacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
