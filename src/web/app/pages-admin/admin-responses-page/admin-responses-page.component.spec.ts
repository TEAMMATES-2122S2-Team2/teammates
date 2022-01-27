import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminResponsesPageComponent } from './admin-responses-page.component';

describe('AdminResponsesPageComponent', () => {
  let component: AdminResponsesPageComponent;
  let fixture: ComponentFixture<AdminResponsesPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminResponsesPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminResponsesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
