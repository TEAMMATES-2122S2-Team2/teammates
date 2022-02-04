import { Component, OnInit } from '@angular/core';
import { FeedbackResponseStatisticsService } from '../../../services/feedback-response-statistics.service';
import { TimezoneService } from '../../../services/timezone.service';
import { FeedbackResponseStatistics, FeedbackResponseStatisticsDateRange } from '../../../types/api-output';
import { DateFormat } from '../../components/datepicker/datepicker.component';
import { TimeFormat } from '../../components/timepicker/timepicker.component';

interface FeedbackResponsesStatisticsFormModel {
  feedbackResponsesDateFrom: DateFormat;
  feedbackResponsesDateTo: DateFormat;
  feedbackResponsesTimeFrom: TimeFormat;
  feedbackResponsesTimeTo: TimeFormat;
}

/**
 * Admin feedback responses statistics page.
 */
@Component({
  selector: 'tm-admin-responses-page',
  templateUrl: './admin-responses-page.component.html',
  styleUrls: ['./admin-responses-page.component.scss'],
})
export class AdminResponsesPageComponent implements OnInit {

  formModel: FeedbackResponsesStatisticsFormModel = {
    feedbackResponsesDateFrom: { year: 0, month: 0, day: 0 },
    feedbackResponsesTimeFrom: { hour: 0, minute: 0 },
    feedbackResponsesDateTo: { year: 0, month: 0, day: 0 },
    feedbackResponsesTimeTo: { hour: 0, minute: 0 },
  };
  dateToday: DateFormat = { year: 0, month: 0, day: 0 };
  firstDate: string = '';
  latestDate: string = '';
  earliestSearchDate: DateFormat = { year: 0, month: 0, day: 0 };
  queryParams: { startTime: number, endTime: number } = { startTime: 0, endTime: 0 };
  hasQueried: boolean = false;
  data: { value: number, date: string }[] = [];

  constructor(
    private feedbackResponseStatisticsService: FeedbackResponseStatisticsService,
    private timezoneService: TimezoneService,
  ) {}

  ngOnInit(): void {
    const now: Date = new Date();
    this.dateToday.year = now.getFullYear();
    this.dateToday.month = now.getMonth() + 1;
    this.dateToday.day = now.getDate();

    // Start with logs from the past hour
    const fromDate: Date = new Date(now.getTime() - 60 * 60 * 1000);

    this.feedbackResponseStatisticsService.getFeedbackResponseStatisticsDataRange()
    .subscribe((dateRange: FeedbackResponseStatisticsDateRange) => {
      this.firstDate = new Date(dateRange.first).toString();
      this.latestDate = new Date(dateRange.latest).toString();
    });

    this.formModel.feedbackResponsesDateFrom = {
      year: fromDate.getFullYear(),
      month: fromDate.getMonth() + 1,
      day: fromDate.getDate(),
    };
    this.formModel.feedbackResponsesDateTo = { ...this.dateToday };
    this.formModel.feedbackResponsesTimeFrom = { hour: fromDate.getHours(), minute: fromDate.getMinutes() };
    this.formModel.feedbackResponsesTimeTo = { hour: now.getHours(), minute: now.getMinutes() };
  }

  getFeedbackResponsesStatistics(): void {
    const timestampFrom: number = this.timezoneService.resolveLocalDateTime(
      this.formModel.feedbackResponsesDateFrom, this.formModel.feedbackResponsesTimeFrom);
    const timestampUntil: number = this.timezoneService.resolveLocalDateTime(
      this.formModel.feedbackResponsesDateTo, this.formModel.feedbackResponsesTimeTo);
    this.queryParams = {
      startTime: timestampFrom,
      endTime: timestampUntil,
    };
    this.feedbackResponseStatisticsService.getFeedbackResponseStatistics(
      this.queryParams,
    ).subscribe((feedbackResponseStatistics: FeedbackResponseStatistics) => {
      this.data = [];
      for (const feedbackResponseStatistic of feedbackResponseStatistics.responses) {
        this.data.push({
          value: feedbackResponseStatistic.numResponses,
          date: new Date(feedbackResponseStatistic.timestamp).toISOString(),
        });
      }
      this.hasQueried = true;
    });
  }

}
