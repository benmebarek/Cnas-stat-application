import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssures, Assures } from 'app/shared/model/assures.model';
import { AssuresService } from './assures.service';
import { AssuresComponent } from './assures.component';
import { AssuresDetailComponent } from './assures-detail.component';
import { AssuresUpdateComponent } from './assures-update.component';

@Injectable({ providedIn: 'root' })
export class AssuresResolve implements Resolve<IAssures> {
  constructor(private service: AssuresService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssures> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((assures: HttpResponse<Assures>) => {
          if (assures.body) {
            return of(assures.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Assures());
  }
}

export const assuresRoute: Routes = [
  {
    path: '',
    component: AssuresComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cnasStatApp.assures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssuresDetailComponent,
    resolve: {
      assures: AssuresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cnasStatApp.assures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssuresUpdateComponent,
    resolve: {
      assures: AssuresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cnasStatApp.assures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssuresUpdateComponent,
    resolve: {
      assures: AssuresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cnasStatApp.assures.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
