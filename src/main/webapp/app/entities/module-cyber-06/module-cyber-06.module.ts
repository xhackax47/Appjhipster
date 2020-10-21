import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppJhipsterSharedModule } from 'app/shared/shared.module';
import { ModuleCyber06Component } from './module-cyber-06.component';
import { ModuleCyber06DetailComponent } from './module-cyber-06-detail.component';
import { ModuleCyber06UpdateComponent } from './module-cyber-06-update.component';
import { ModuleCyber06DeleteDialogComponent } from './module-cyber-06-delete-dialog.component';
import { moduleRoute } from './module-cyber-06.route';

@NgModule({
  imports: [AppJhipsterSharedModule, RouterModule.forChild(moduleRoute)],
  declarations: [ModuleCyber06Component, ModuleCyber06DetailComponent, ModuleCyber06UpdateComponent, ModuleCyber06DeleteDialogComponent],
  entryComponents: [ModuleCyber06DeleteDialogComponent],
})
export class AppJhipsterModuleCyber06Module {}
