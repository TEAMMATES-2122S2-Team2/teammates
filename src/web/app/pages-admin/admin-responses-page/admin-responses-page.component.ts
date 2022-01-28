import { Component, OnInit } from '@angular/core';
import { FeedbackResponseStatisticsService } from '../../../services/feedback-response-statistics.service';
import { TimezoneService } from '../../../services/timezone.service';
import { FeedbackResponseStatistics } from '../../../types/api-output';
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
  earliestSearchDate: DateFormat = { year: 0, month: 0, day: 0 };
  queryParams: { startTime: number, endTime: number } = { startTime: 0, endTime: 0 };

  // TODO: This is a placeholder.
  data: { value: number, date: string }[] = [
    {
      value: 20,
      date: '2020-05-12T12:19:00+00:00',
    },
    {
      value: 50,
      date: '2020-05-14T12:19:00+00:00',
    },
    {
      value: 30,
      date: '2020-05-16T12:19:00+00:00',
    },
    {
      value: 80,
      date: '2020-05-18T12:19:00+00:00',
    },
    {
      value: 55,
      date: '2020-05-20T12:19:00+00:00',
    },
    {
      value: 60,
      date: '2020-05-22T12:19:00+00:00',
    },
    {
      value: 45,
      date: '2020-05-24T12:19:00+00:00',
    },
    {
      value: 30,
      date: '2020-05-26T12:19:00+00:00',
    },
    {
      value: 40,
      date: '2020-05-28T12:19:00+00:00',
    },
    {
      value: 70,
      date: '2020-05-30T12:19:00+00:00',
    },
    {
      value: 63,
      date: '2020-06-01T12:19:00+00:00',
    },
    {
      value: 40,
      date: '2020-06-03T12:19:00+00:00',
    },
    {
      value: 50,
      date: '2020-06-05T12:19:00+00:00',
    },
    {
      value: 75,
      date: '2020-06-07T12:19:00+00:00',
    },
    {
      value: 20,
      date: '2020-06-09T12:19:00+00:00',
    },
    {
      value: 50,
      date: '2020-06-11T12:19:00+00:00',
    },
    {
      value: 80,
      date: '2020-06-13T12:19:00+00:00',
    },
    {
      value: 75,
      date: '2020-06-15T12:19:00+00:00',
    },
    {
      value: 82,
      date: '2020-06-17T12:19:00+00:00',
    },
    {
      value: 55,
      date: '2020-06-19T12:19:00+00:00',
    },
    {
      value: 35,
      date: '2020-06-21T12:19:00+00:00',
    },
    {
      value: 34,
      date: '2020-06-23T12:19:00+00:00',
    },
    {
      value: 45,
      date: '2020-06-25T12:19:00+00:00',
    },
    {
      value: 58,
      date: '2020-06-27T12:19:00+00:00',
    },
    {
      value: 34,
      date: '2020-06-29T12:19:00+00:00',
    },
    {
      value: 60,
      date: '2020-07-01T12:19:00+00:00',
    },
    {
      value: 75,
      date: '2020-07-03T12:19:00+00:00',
    },
    {
      value: 80,
      date: '2020-07-05T12:19:00+00:00',
    },
    {
      value: 29,
      date: '2020-07-07T12:19:00+00:00',
    },
    {
      value: 40,
      date: '2020-07-09T12:19:00+00:00',
    },
    {
      value: 54,
      date: '2020-07-11T12:19:00+00:00',
    },
    {
      value: 67,
      date: '2020-07-13T12:19:00+00:00',
    },
    {
      value: 90,
      date: '2020-07-15T12:19:00+00:00',
    },
    {
      value: 84,
      date: '2020-07-17T12:19:00+00:00',
    },
    {
      value: 43,
      date: '2020-07-19T12:19:00+00:00',
    },
  ];

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
    });
  }

}
