import { TestBed } from '@angular/core/testing';

import { FeedbackResponseStatisticsService } from './feedback-response-statistics.service';

describe('FeedbackResponseStatisticsService', () => {
  let service: FeedbackResponseStatisticsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeedbackResponseStatisticsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
