import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStyle } from '../style.model';
import { StyleService } from '../service/style.service';

@Injectable({ providedIn: 'root' })
export class StyleRoutingResolveService implements Resolve<IStyle | null> {
  constructor(protected service: StyleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStyle | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((style: HttpResponse<IStyle>) => {
          if (style.body) {
            return of(style.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
