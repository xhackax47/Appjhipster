import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'devis-cyber-06',
        loadChildren: () => import('./devis-cyber-06/devis-cyber-06.module').then(m => m.AppJhipsterDevisCyber06Module),
      },
      {
        path: 'client-cyber-06',
        loadChildren: () => import('./client-cyber-06/client-cyber-06.module').then(m => m.AppJhipsterClientCyber06Module),
      },
      {
        path: 'commercial-cyber-06',
        loadChildren: () => import('./commercial-cyber-06/commercial-cyber-06.module').then(m => m.AppJhipsterCommercialCyber06Module),
      },
      {
        path: 'module-cyber-06',
        loadChildren: () => import('./module-cyber-06/module-cyber-06.module').then(m => m.AppJhipsterModuleCyber06Module),
      },
      {
        path: 'composant-cyber-06',
        loadChildren: () => import('./composant-cyber-06/composant-cyber-06.module').then(m => m.AppJhipsterComposantCyber06Module),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AppJhipsterEntityModule {}
