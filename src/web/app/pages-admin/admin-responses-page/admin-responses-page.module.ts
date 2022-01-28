import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminResponsesPageComponent } from './admin-responses-page.component';
import { ResponsesLineChartComponent } from './responses-line-chart/responses-line-chart.component';

const routes: Routes = [
  {
    path: '',
    component: AdminResponsesPageComponent,
  },
];

/**
 * Module for admin feedback responses statistics page.
 */
@NgModule({
  declarations: [
    AdminResponsesPageComponent,
    ResponsesLineChartComponent,
  ],
  exports: [
    AdminResponsesPageComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ],
})
export class AdminResponsesPageModule { }
