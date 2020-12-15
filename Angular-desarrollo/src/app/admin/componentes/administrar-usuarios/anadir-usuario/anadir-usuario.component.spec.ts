import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnadirUsuarioComponent } from './anadir-usuario.component';

describe('AnadirUsuarioComponent', () => {
  let component: AnadirUsuarioComponent;
  let fixture: ComponentFixture<AnadirUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnadirUsuarioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnadirUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
