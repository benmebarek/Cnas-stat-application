import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CnasStatSharedModule } from 'app/shared/shared.module';
import { AssuresComponent } from './assures.component';
import { AssuresDetailComponent } from './assures-detail.component';
import { AssuresUpdateComponent } from './assures-update.component';
import { AssuresDeleteDialogComponent } from './assures-delete-dialog.component';
import { assuresRoute } from './assures.route';

@NgModule({
  imports: [CnasStatSharedModule, RouterModule.forChild(assuresRoute)],
  declarations: [AssuresComponent, AssuresDetailComponent, AssuresUpdateComponent, AssuresDeleteDialogComponent],
  entryComponents: [AssuresDeleteDialogComponent]
})
export class CnasStatAssuresModule {}
