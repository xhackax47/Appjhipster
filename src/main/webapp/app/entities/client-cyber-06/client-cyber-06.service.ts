import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClientCyber06 } from 'app/shared/model/client-cyber-06.model';

type EntityResponseType = HttpResponse<IClientCyber06>;
type EntityArrayResponseType = HttpResponse<IClientCyber06[]>;

@Injectable({ providedIn: 'root' })
export class ClientCyber06Service {
  public resourceUrl = SERVER_API_URL + 'api/clients';

  constructor(protected http: HttpClient) {}

  create(client: IClientCyber06): Observable<EntityResponseType> {
    return this.http.post<IClientCyber06>(this.resourceUrl, client, { observe: 'response' });
  }

  update(client: IClientCyber06): Observable<EntityResponseType> {
    return this.http.put<IClientCyber06>(this.resourceUrl, client, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClientCyber06>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClientCyber06[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
