import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StartappComponent } from './startapp.component';

describe('StartappComponent', () => {
  let component: StartappComponent;
  let fixture: ComponentFixture<StartappComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StartappComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartappComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
