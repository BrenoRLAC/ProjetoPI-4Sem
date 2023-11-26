import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LibrasComponent } from './libras.component';

describe('LibrasComponent', () => {
  let component: LibrasComponent;
  let fixture: ComponentFixture<LibrasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LibrasComponent]
    });
    fixture = TestBed.createComponent(LibrasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
