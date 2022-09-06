import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StyleComponent } from './list/style.component';
import { StyleDetailComponent } from './detail/style-detail.component';
import { StyleUpdateComponent } from './update/style-update.component';
import { StyleDeleteDialogComponent } from './delete/style-delete-dialog.component';
import { StyleRoutingModule } from './route/style-routing.module';

@NgModule({
  imports: [SharedModule, StyleRoutingModule],
  declarations: [StyleComponent, StyleDetailComponent, StyleUpdateComponent, StyleDeleteDialogComponent],
})
export class StyleModule {}
