import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassresetComponent } from './passreset.component';

describe('PassresetComponent', () => {
  let component: PassresetComponent;
  let fixture: ComponentFixture<PassresetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassresetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassresetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
