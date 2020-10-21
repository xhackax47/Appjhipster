import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDevisCyber06 } from 'app/shared/model/devis-cyber-06.model';

type EntityResponseType = HttpResponse<IDevisCyber06>;
type EntityArrayResponseType = HttpResponse<IDevisCyber06[]>;

@Injectable({ providedIn: 'root' })
export class DevisCyber06Service {
  public resourceUrl = SERVER_API_URL + 'api/devis';

  constructor(protected http: HttpClient) {}

  create(devis: IDevisCyber06): Observable<EntityResponseType> {
    return this.http.post<IDevisCyber06>(this.resourceUrl, devis, { observe: 'response' });
  }

  update(devis: IDevisCyber06): Observable<EntityResponseType> {
    return this.http.put<IDevisCyber06>(this.resourceUrl, devis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDevisCyber06>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDevisCyber06[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
