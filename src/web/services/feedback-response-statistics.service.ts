import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResourceEndpoints } from '../types/api-const';
import { FeedbackResponseStatistics } from '../types/api-output';
import { HttpRequestService } from './http-request.service';

/**
 * Handles feedback response statistics provision.
 */
@Injectable({
  providedIn: 'root',
})
export class FeedbackResponseStatisticsService {

  constructor(private httpRequestService: HttpRequestService) { }

  getFeedbackResponseStatistics(queryParams: {
    startTime: number,
    endTime: number,
  }): Observable<FeedbackResponseStatistics> {
    const paramMap: Record<string, string> = {
      starttime: `${queryParams.startTime}`,
      endtime: `${queryParams.endTime}`,
    };

    return this.httpRequestService.get(ResourceEndpoints.RESPONSE_STATISTICS, paramMap);
  }
}
