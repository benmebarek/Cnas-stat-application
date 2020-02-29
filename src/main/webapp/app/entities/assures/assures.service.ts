import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAssures } from 'app/shared/model/assures.model';

type EntityResponseType = HttpResponse<IAssures>;
type EntityArrayResponseType = HttpResponse<IAssures[]>;

@Injectable({ providedIn: 'root' })
export class AssuresService {
  public resourceUrl = SERVER_API_URL + 'api/assures';

  constructor(protected http: HttpClient) {}

  create(assures: IAssures): Observable<EntityResponseType> {
    return this.http.post<IAssures>(this.resourceUrl, assures, { observe: 'response' });
  }

  update(assures: IAssures): Observable<EntityResponseType> {
    return this.http.put<IAssures>(this.resourceUrl, assures, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssures[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
