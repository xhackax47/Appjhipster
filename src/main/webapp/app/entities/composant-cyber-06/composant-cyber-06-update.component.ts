import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IComposantCyber06, ComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';
import { ComposantCyber06Service } from './composant-cyber-06.service';

@Component({
  selector: 'jhi-composant-cyber-06-update',
  templateUrl: './composant-cyber-06-update.component.html',
})
export class ComposantCyber06UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected composantService: ComposantCyber06Service, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ composant }) => {
      this.updateForm(composant);
    });
  }

  updateForm(composant: IComposantCyber06): void {
    this.editForm.patchValue({
      id: composant.id,
      name: composant.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const composant = this.createFromForm();
    if (composant.id !== undefined) {
      this.subscribeToSaveResponse(this.composantService.update(composant));
    } else {
      this.subscribeToSaveResponse(this.composantService.create(composant));
    }
  }

  private createFromForm(): IComposantCyber06 {
    return {
      ...new ComposantCyber06(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComposantCyber06>>): void {
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
