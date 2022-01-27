import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminResponsesPageComponent } from './admin-responses-page.component';
import { RouterModule, Routes } from "@angular/router";
import { ResponsesLineChartComponent } from './responses-line-chart/responses-line-chart.component';

const routes: Routes = [
  {
    path: '',
    component: AdminResponsesPageComponent,
  },
];

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
  ]
})
export class AdminResponsesPageModule { }
