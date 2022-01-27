import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminResponsesPageComponent } from './admin-responses-page.component';
import { RouterModule, Routes } from "@angular/router";

const routes: Routes = [
  {
    path: '',
    component: AdminResponsesPageComponent,
  },
];

@NgModule({
  declarations: [
    AdminResponsesPageComponent,
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
