import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllFollowersComponent } from './all-followers.component';

describe('AllFollowersComponent', () => {
  let component: AllFollowersComponent;
  let fixture: ComponentFixture<AllFollowersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllFollowersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllFollowersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
