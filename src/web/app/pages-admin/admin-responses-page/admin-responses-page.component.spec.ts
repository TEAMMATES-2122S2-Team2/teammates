import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NgbDatepickerModule, NgbTimepickerModule } from '@ng-bootstrap/ng-bootstrap';

import { AdminResponsesPageComponent } from './admin-responses-page.component';
import { ResponsesLineChartComponent } from './responses-line-chart/responses-line-chart.component';

describe('AdminResponsesPageComponent', () => {
  let component: AdminResponsesPageComponent;
  let fixture: ComponentFixture<AdminResponsesPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AdminResponsesPageComponent,
        ResponsesLineChartComponent,
      ],
      imports: [
        NgbDatepickerModule,
        NgbTimepickerModule,
        FormsModule,
        HttpClientTestingModule,
      ],
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
