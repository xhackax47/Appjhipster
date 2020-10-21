import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppJhipsterSharedModule } from 'app/shared/shared.module';
import { DevisCyber06Component } from './devis-cyber-06.component';
import { DevisCyber06DetailComponent } from './devis-cyber-06-detail.component';
import { DevisCyber06UpdateComponent } from './devis-cyber-06-update.component';
import { DevisCyber06DeleteDialogComponent } from './devis-cyber-06-delete-dialog.component';
import { devisRoute } from './devis-cyber-06.route';

@NgModule({
  imports: [AppJhipsterSharedModule, RouterModule.forChild(devisRoute)],
  declarations: [DevisCyber06Component, DevisCyber06DetailComponent, DevisCyber06UpdateComponent, DevisCyber06DeleteDialogComponent],
  entryComponents: [DevisCyber06DeleteDialogComponent],
})
export class AppJhipsterDevisCyber06Module {}
