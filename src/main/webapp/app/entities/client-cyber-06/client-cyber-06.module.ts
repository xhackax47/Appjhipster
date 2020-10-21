import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppJhipsterSharedModule } from 'app/shared/shared.module';
import { ClientCyber06Component } from './client-cyber-06.component';
import { ClientCyber06DetailComponent } from './client-cyber-06-detail.component';
import { ClientCyber06UpdateComponent } from './client-cyber-06-update.component';
import { ClientCyber06DeleteDialogComponent } from './client-cyber-06-delete-dialog.component';
import { clientRoute } from './client-cyber-06.route';

@NgModule({
  imports: [AppJhipsterSharedModule, RouterModule.forChild(clientRoute)],
  declarations: [ClientCyber06Component, ClientCyber06DetailComponent, ClientCyber06UpdateComponent, ClientCyber06DeleteDialogComponent],
  entryComponents: [ClientCyber06DeleteDialogComponent],
})
export class AppJhipsterClientCyber06Module {}
