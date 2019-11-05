import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllSongComponent } from './all-song.component';

describe('AllSongComponent', () => {
  let component: AllSongComponent;
  let fixture: ComponentFixture<AllSongComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllSongComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllSongComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
