import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CirculacionComponent } from './circulacion.component';

describe('CirculacionComponent', () => {
  let component: CirculacionComponent;
  let fixture: ComponentFixture<CirculacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CirculacionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CirculacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
