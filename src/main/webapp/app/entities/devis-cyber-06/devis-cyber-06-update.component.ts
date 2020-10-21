import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDevisCyber06, DevisCyber06 } from 'app/shared/model/devis-cyber-06.model';
import { DevisCyber06Service } from './devis-cyber-06.service';

@Component({
  selector: 'jhi-devis-cyber-06-update',
  templateUrl: './devis-cyber-06-update.component.html',
})
export class DevisCyber06UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
  });

  constructor(protected devisService: DevisCyber06Service, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ devis }) => {
      this.updateForm(devis);
    });
  }

  updateForm(devis: IDevisCyber06): void {
    this.editForm.patchValue({
      id: devis.id,
      name: devis.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const devis = this.createFromForm();
    if (devis.id !== undefined) {
      this.subscribeToSaveResponse(this.devisService.update(devis));
    } else {
      this.subscribeToSaveResponse(this.devisService.create(devis));
    }
  }

  private createFromForm(): IDevisCyber06 {
    return {
      ...new DevisCyber06(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevisCyber06>>): void {
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
