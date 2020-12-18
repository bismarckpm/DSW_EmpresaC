import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarTiposComponent } from './administrar-tipos.component';

describe('AdministrarTiposComponent', () => {
  let component: AdministrarTiposComponent;
  let fixture: ComponentFixture<AdministrarTiposComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrarTiposComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarTiposComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
