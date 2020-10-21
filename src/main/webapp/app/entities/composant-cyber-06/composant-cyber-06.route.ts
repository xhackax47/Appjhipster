import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComposantCyber06, ComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';
import { ComposantCyber06Service } from './composant-cyber-06.service';
import { ComposantCyber06Component } from './composant-cyber-06.component';
import { ComposantCyber06DetailComponent } from './composant-cyber-06-detail.component';
import { ComposantCyber06UpdateComponent } from './composant-cyber-06-update.component';

@Injectable({ providedIn: 'root' })
export class ComposantCyber06Resolve implements Resolve<IComposantCyber06> {
  constructor(private service: ComposantCyber06Service, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComposantCyber06> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((composant: HttpResponse<ComposantCyber06>) => {
          if (composant.body) {
            return of(composant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ComposantCyber06());
  }
}

export const composantRoute: Routes = [
  {
    path: '',
    component: ComposantCyber06Component,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.composant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComposantCyber06DetailComponent,
    resolve: {
      composant: ComposantCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.composant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComposantCyber06UpdateComponent,
    resolve: {
      composant: ComposantCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.composant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComposantCyber06UpdateComponent,
    resolve: {
      composant: ComposantCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.composant.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
