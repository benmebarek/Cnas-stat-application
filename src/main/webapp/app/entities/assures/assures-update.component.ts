import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAssures, Assures } from 'app/shared/model/assures.model';
import { AssuresService } from './assures.service';

@Component({
  selector: 'jhi-assures-update',
  templateUrl: './assures-update.component.html'
})
export class AssuresUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    noassure: [],
    nom: [],
    prenom: []
  });

  constructor(protected assuresService: AssuresService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assures }) => {
      this.updateForm(assures);
    });
  }

  updateForm(assures: IAssures): void {
    this.editForm.patchValue({
      id: assures.id,
      noassure: assures.noassure,
      nom: assures.nom,
      prenom: assures.prenom
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assures = this.createFromForm();
    if (assures.id !== undefined) {
      this.subscribeToSaveResponse(this.assuresService.update(assures));
    } else {
      this.subscribeToSaveResponse(this.assuresService.create(assures));
    }
  }

  private createFromForm(): IAssures {
    return {
      ...new Assures(),
      id: this.editForm.get(['id'])!.value,
      noassure: this.editForm.get(['noassure'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssures>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
