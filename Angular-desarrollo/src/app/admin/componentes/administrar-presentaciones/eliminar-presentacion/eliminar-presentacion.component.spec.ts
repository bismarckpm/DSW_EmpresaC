import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EliminarPresentacionComponent } from './eliminar-presentacion.component';

describe('EliminarPresentacionComponent', () => {
  let component: EliminarPresentacionComponent;
  let fixture: ComponentFixture<EliminarPresentacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EliminarPresentacionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EliminarPresentacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
