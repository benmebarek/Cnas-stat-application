import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssures } from 'app/shared/model/assures.model';
import { AssuresService } from './assures.service';

@Component({
  templateUrl: './assures-delete-dialog.component.html'
})
export class AssuresDeleteDialogComponent {
  assures?: IAssures;

  constructor(protected assuresService: AssuresService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assuresService.delete(id).subscribe(() => {
      this.eventManager.broadcast('assuresListModification');
      this.activeModal.close();
    });
  }
}
