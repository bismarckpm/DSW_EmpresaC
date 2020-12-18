import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnadirAdminAnalistaComponent } from './anadir-admin-analista.component';

describe('AnadirAdminAnalistaComponent', () => {
  let component: AnadirAdminAnalistaComponent;
  let fixture: ComponentFixture<AnadirAdminAnalistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnadirAdminAnalistaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnadirAdminAnalistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
