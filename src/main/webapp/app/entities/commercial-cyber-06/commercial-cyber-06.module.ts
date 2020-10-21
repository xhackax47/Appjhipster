import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppJhipsterSharedModule } from 'app/shared/shared.module';
import { CommercialCyber06Component } from './commercial-cyber-06.component';
import { CommercialCyber06DetailComponent } from './commercial-cyber-06-detail.component';
import { CommercialCyber06UpdateComponent } from './commercial-cyber-06-update.component';
import { CommercialCyber06DeleteDialogComponent } from './commercial-cyber-06-delete-dialog.component';
import { commercialRoute } from './commercial-cyber-06.route';

@NgModule({
  imports: [AppJhipsterSharedModule, RouterModule.forChild(commercialRoute)],
  declarations: [
    CommercialCyber06Component,
    CommercialCyber06DetailComponent,
    CommercialCyber06UpdateComponent,
    CommercialCyber06DeleteDialogComponent,
  ],
  entryComponents: [CommercialCyber06DeleteDialogComponent],
})
export class AppJhipsterCommercialCyber06Module {}
