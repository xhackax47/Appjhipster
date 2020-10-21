import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';

type EntityResponseType = HttpResponse<IComposantCyber06>;
type EntityArrayResponseType = HttpResponse<IComposantCyber06[]>;

@Injectable({ providedIn: 'root' })
export class ComposantCyber06Service {
  public resourceUrl = SERVER_API_URL + 'api/composants';

  constructor(protected http: HttpClient) {}

  create(composant: IComposantCyber06): Observable<EntityResponseType> {
    return this.http.post<IComposantCyber06>(this.resourceUrl, composant, { observe: 'response' });
  }

  update(composant: IComposantCyber06): Observable<EntityResponseType> {
    return this.http.put<IComposantCyber06>(this.resourceUrl, composant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComposantCyber06>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComposantCyber06[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
