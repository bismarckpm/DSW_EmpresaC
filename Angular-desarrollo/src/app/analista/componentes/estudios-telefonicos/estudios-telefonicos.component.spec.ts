import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstudiosTelefonicosComponent } from './estudios-telefonicos.component';

describe('EstudiosTelefonicosComponent', () => {
  let component: EstudiosTelefonicosComponent;
  let fixture: ComponentFixture<EstudiosTelefonicosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstudiosTelefonicosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstudiosTelefonicosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
