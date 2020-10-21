import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModuleCyber06 } from 'app/shared/model/module-cyber-06.model';

type EntityResponseType = HttpResponse<IModuleCyber06>;
type EntityArrayResponseType = HttpResponse<IModuleCyber06[]>;

@Injectable({ providedIn: 'root' })
export class ModuleCyber06Service {
  public resourceUrl = SERVER_API_URL + 'api/modules';

  constructor(protected http: HttpClient) {}

  create(module: IModuleCyber06): Observable<EntityResponseType> {
    return this.http.post<IModuleCyber06>(this.resourceUrl, module, { observe: 'response' });
  }

  update(module: IModuleCyber06): Observable<EntityResponseType> {
    return this.http.put<IModuleCyber06>(this.resourceUrl, module, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModuleCyber06>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModuleCyber06[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
