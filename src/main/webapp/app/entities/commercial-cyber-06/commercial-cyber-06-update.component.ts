import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommercialCyber06, CommercialCyber06 } from 'app/shared/model/commercial-cyber-06.model';
import { CommercialCyber06Service } from './commercial-cyber-06.service';

@Component({
  selector: 'jhi-commercial-cyber-06-update',
  templateUrl: './commercial-cyber-06-update.component.html',
})
export class CommercialCyber06UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstname: [],
    name: [null, [Validators.required]],
  });

  constructor(protected commercialService: CommercialCyber06Service, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commercial }) => {
      this.updateForm(commercial);
    });
  }

  updateForm(commercial: ICommercialCyber06): void {
    this.editForm.patchValue({
      id: commercial.id,
      firstname: commercial.firstname,
      name: commercial.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commercial = this.createFromForm();
    if (commercial.id !== undefined) {
      this.subscribeToSaveResponse(this.commercialService.update(commercial));
    } else {
      this.subscribeToSaveResponse(this.commercialService.create(commercial));
    }
  }

  private createFromForm(): ICommercialCyber06 {
    return {
      ...new CommercialCyber06(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialCyber06>>): void {
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
