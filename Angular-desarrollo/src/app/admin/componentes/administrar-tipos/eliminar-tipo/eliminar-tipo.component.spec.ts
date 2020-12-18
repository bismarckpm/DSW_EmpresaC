import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EliminarTipoComponent } from './eliminar-tipo.component';

describe('EliminarTipoComponent', () => {
  let component: EliminarTipoComponent;
  let fixture: ComponentFixture<EliminarTipoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EliminarTipoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EliminarTipoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
