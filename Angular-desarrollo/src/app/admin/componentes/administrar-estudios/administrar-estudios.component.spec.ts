import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarEstudiosComponent } from './administrar-estudios.component';

describe('AdministrarEstudiosComponent', () => {
  let component: AdministrarEstudiosComponent;
  let fixture: ComponentFixture<AdministrarEstudiosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdministrarEstudiosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrarEstudiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
