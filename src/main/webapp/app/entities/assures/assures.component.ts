import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssures } from 'app/shared/model/assures.model';
import { AssuresService } from './assures.service';
import { AssuresDeleteDialogComponent } from './assures-delete-dialog.component';

@Component({
  selector: 'jhi-assures',
  templateUrl: './assures.component.html'
})
export class AssuresComponent implements OnInit, OnDestroy {
  assures?: IAssures[];
  eventSubscriber?: Subscription;

  constructor(protected assuresService: AssuresService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.assuresService.query().subscribe((res: HttpResponse<IAssures[]>) => (this.assures = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAssures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAssures): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAssures(): void {
    this.eventSubscriber = this.eventManager.subscribe('assuresListModification', () => this.loadAll());
  }

  delete(assures: IAssures): void {
    const modalRef = this.modalService.open(AssuresDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.assures = assures;
  }
}
