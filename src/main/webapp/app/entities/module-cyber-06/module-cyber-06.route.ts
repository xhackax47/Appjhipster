import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModuleCyber06, ModuleCyber06 } from 'app/shared/model/module-cyber-06.model';
import { ModuleCyber06Service } from './module-cyber-06.service';
import { ModuleCyber06Component } from './module-cyber-06.component';
import { ModuleCyber06DetailComponent } from './module-cyber-06-detail.component';
import { ModuleCyber06UpdateComponent } from './module-cyber-06-update.component';

@Injectable({ providedIn: 'root' })
export class ModuleCyber06Resolve implements Resolve<IModuleCyber06> {
  constructor(private service: ModuleCyber06Service, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModuleCyber06> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((module: HttpResponse<ModuleCyber06>) => {
          if (module.body) {
            return of(module.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ModuleCyber06());
  }
}

export const moduleRoute: Routes = [
  {
    path: '',
    component: ModuleCyber06Component,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.module.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModuleCyber06DetailComponent,
    resolve: {
      module: ModuleCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.module.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModuleCyber06UpdateComponent,
    resolve: {
      module: ModuleCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.module.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModuleCyber06UpdateComponent,
    resolve: {
      module: ModuleCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.module.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
