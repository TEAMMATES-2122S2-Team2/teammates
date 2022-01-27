import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResponsesLineChartComponent } from './responses-line-chart.component';

describe('ResponsesLineChartComponent', () => {
  let component: ResponsesLineChartComponent;
  let fixture: ComponentFixture<ResponsesLineChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResponsesLineChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResponsesLineChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
