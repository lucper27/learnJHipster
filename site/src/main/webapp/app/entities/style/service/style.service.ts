import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStyle, NewStyle } from '../style.model';

export type PartialUpdateStyle = Partial<IStyle> & Pick<IStyle, 'id'>;

export type EntityResponseType = HttpResponse<IStyle>;
export type EntityArrayResponseType = HttpResponse<IStyle[]>;

@Injectable({ providedIn: 'root' })
export class StyleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/styles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(style: NewStyle): Observable<EntityResponseType> {
    return this.http.post<IStyle>(this.resourceUrl, style, { observe: 'response' });
  }

  update(style: IStyle): Observable<EntityResponseType> {
    return this.http.put<IStyle>(`${this.resourceUrl}/${this.getStyleIdentifier(style)}`, style, { observe: 'response' });
  }

  partialUpdate(style: PartialUpdateStyle): Observable<EntityResponseType> {
    return this.http.patch<IStyle>(`${this.resourceUrl}/${this.getStyleIdentifier(style)}`, style, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStyle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStyle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStyleIdentifier(style: Pick<IStyle, 'id'>): number {
    return style.id;
  }

  compareStyle(o1: Pick<IStyle, 'id'> | null, o2: Pick<IStyle, 'id'> | null): boolean {
    return o1 && o2 ? this.getStyleIdentifier(o1) === this.getStyleIdentifier(o2) : o1 === o2;
  }

  addStyleToCollectionIfMissing<Type extends Pick<IStyle, 'id'>>(
    styleCollection: Type[],
    ...stylesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const styles: Type[] = stylesToCheck.filter(isPresent);
    if (styles.length > 0) {
      const styleCollectionIdentifiers = styleCollection.map(styleItem => this.getStyleIdentifier(styleItem)!);
      const stylesToAdd = styles.filter(styleItem => {
        const styleIdentifier = this.getStyleIdentifier(styleItem);
        if (styleCollectionIdentifiers.includes(styleIdentifier)) {
          return false;
        }
        styleCollectionIdentifiers.push(styleIdentifier);
        return true;
      });
      return [...stylesToAdd, ...styleCollection];
    }
    return styleCollection;
  }
}
