import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDevisCyber06, DevisCyber06 } from 'app/shared/model/devis-cyber-06.model';
import { DevisCyber06Service } from './devis-cyber-06.service';
import { DevisCyber06Component } from './devis-cyber-06.component';
import { DevisCyber06DetailComponent } from './devis-cyber-06-detail.component';
import { DevisCyber06UpdateComponent } from './devis-cyber-06-update.component';

@Injectable({ providedIn: 'root' })
export class DevisCyber06Resolve implements Resolve<IDevisCyber06> {
  constructor(private service: DevisCyber06Service, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevisCyber06> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((devis: HttpResponse<DevisCyber06>) => {
          if (devis.body) {
            return of(devis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DevisCyber06());
  }
}

export const devisRoute: Routes = [
  {
    path: '',
    component: DevisCyber06Component,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.devis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DevisCyber06DetailComponent,
    resolve: {
      devis: DevisCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.devis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DevisCyber06UpdateComponent,
    resolve: {
      devis: DevisCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.devis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DevisCyber06UpdateComponent,
    resolve: {
      devis: DevisCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.devis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
