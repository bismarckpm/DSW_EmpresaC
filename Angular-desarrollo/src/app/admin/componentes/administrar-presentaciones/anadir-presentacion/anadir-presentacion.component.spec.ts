import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnadirPresentacionComponent } from './anadir-presentacion.component';

describe('AnadirPresentacionComponent', () => {
  let component: AnadirPresentacionComponent;
  let fixture: ComponentFixture<AnadirPresentacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnadirPresentacionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnadirPresentacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
