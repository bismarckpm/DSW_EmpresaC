import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnadirModalComponent } from './anadir-modal.component';

describe('AnadirModalComponent', () => {
  let component: AnadirModalComponent;
  let fixture: ComponentFixture<AnadirModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnadirModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnadirModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
