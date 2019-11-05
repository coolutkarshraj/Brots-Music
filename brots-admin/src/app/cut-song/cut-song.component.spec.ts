import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CutSongComponent } from './cut-song.component';

describe('CutSongComponent', () => {
  let component: CutSongComponent;
  let fixture: ComponentFixture<CutSongComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CutSongComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CutSongComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
