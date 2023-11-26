import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertVideoComponent } from './insert-video.component';

describe('InsertVideoComponent', () => {
  let component: InsertVideoComponent;
  let fixture: ComponentFixture<InsertVideoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InsertVideoComponent]
    });
    fixture = TestBed.createComponent(InsertVideoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
