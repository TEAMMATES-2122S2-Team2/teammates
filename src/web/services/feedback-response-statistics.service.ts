import { Injectable } from '@angular/core';
import { HttpRequestService } from "./http-request.service";
import { Observable } from "rxjs";
import { FeedbackResponseStatistics } from "../types/api-output";
import { ResourceEndpoints } from "../types/api-const";

@Injectable({
  providedIn: 'root'
})
export class FeedbackResponseStatisticsService {

  constructor(private httpRequestService: HttpRequestService) { }

  getFeedbackResponseStatistics(queryParams: {
    startTime: string,
    endTime: string,
  }): Observable<FeedbackResponseStatistics> {
    const paramMap: Record<string, string> = {
      starttime: queryParams.startTime,
      endtime: queryParams.endTime,
    };

    return this.httpRequestService.get(ResourceEndpoints.RESPONSE_STATISTICS, paramMap);
  }
}
