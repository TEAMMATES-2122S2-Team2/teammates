import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { FeedbackResponseStatisticsService } from './feedback-response-statistics.service';

describe('FeedbackResponseStatisticsService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
    ],
  }));

  it('should be created', () => {
    const service: FeedbackResponseStatisticsService = TestBed.inject(FeedbackResponseStatisticsService);
    expect(service).toBeTruthy();
  });
});
