import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssures } from 'app/shared/model/assures.model';

@Component({
  selector: 'jhi-assures-detail',
  templateUrl: './assures-detail.component.html'
})
export class AssuresDetailComponent implements OnInit {
  assures: IAssures | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assures }) => (this.assures = assures));
  }

  previousState(): void {
    window.history.back();
  }
}
