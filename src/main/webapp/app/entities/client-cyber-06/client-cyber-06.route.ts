import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClientCyber06, ClientCyber06 } from 'app/shared/model/client-cyber-06.model';
import { ClientCyber06Service } from './client-cyber-06.service';
import { ClientCyber06Component } from './client-cyber-06.component';
import { ClientCyber06DetailComponent } from './client-cyber-06-detail.component';
import { ClientCyber06UpdateComponent } from './client-cyber-06-update.component';

@Injectable({ providedIn: 'root' })
export class ClientCyber06Resolve implements Resolve<IClientCyber06> {
  constructor(private service: ClientCyber06Service, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClientCyber06> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((client: HttpResponse<ClientCyber06>) => {
          if (client.body) {
            return of(client.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClientCyber06());
  }
}

export const clientRoute: Routes = [
  {
    path: '',
    component: ClientCyber06Component,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.client.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClientCyber06DetailComponent,
    resolve: {
      client: ClientCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.client.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClientCyber06UpdateComponent,
    resolve: {
      client: ClientCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.client.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClientCyber06UpdateComponent,
    resolve: {
      client: ClientCyber06Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'appJhipsterApp.client.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
