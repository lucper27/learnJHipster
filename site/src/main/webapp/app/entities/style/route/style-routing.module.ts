import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StyleComponent } from '../list/style.component';
import { StyleDetailComponent } from '../detail/style-detail.component';
import { StyleUpdateComponent } from '../update/style-update.component';
import { StyleRoutingResolveService } from './style-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const styleRoute: Routes = [
  {
    path: '',
    component: StyleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StyleDetailComponent,
    resolve: {
      style: StyleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StyleUpdateComponent,
    resolve: {
      style: StyleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StyleUpdateComponent,
    resolve: {
      style: StyleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(styleRoute)],
  exports: [RouterModule],
})
export class StyleRoutingModule {}
