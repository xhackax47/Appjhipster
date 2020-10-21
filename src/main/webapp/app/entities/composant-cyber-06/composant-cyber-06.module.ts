import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppJhipsterSharedModule } from 'app/shared/shared.module';
import { ComposantCyber06Component } from './composant-cyber-06.component';
import { ComposantCyber06DetailComponent } from './composant-cyber-06-detail.component';
import { ComposantCyber06UpdateComponent } from './composant-cyber-06-update.component';
import { ComposantCyber06DeleteDialogComponent } from './composant-cyber-06-delete-dialog.component';
import { composantRoute } from './composant-cyber-06.route';

@NgModule({
  imports: [AppJhipsterSharedModule, RouterModule.forChild(composantRoute)],
  declarations: [
    ComposantCyber06Component,
    ComposantCyber06DetailComponent,
    ComposantCyber06UpdateComponent,
    ComposantCyber06DeleteDialogComponent,
  ],
  entryComponents: [ComposantCyber06DeleteDialogComponent],
})
export class AppJhipsterComposantCyber06Module {}
