import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';
import { ComposantCyber06Service } from './composant-cyber-06.service';

@Component({
  templateUrl: './composant-cyber-06-delete-dialog.component.html',
})
export class ComposantCyber06DeleteDialogComponent {
  composant?: IComposantCyber06;

  constructor(
    protected composantService: ComposantCyber06Service,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.composantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('composantListModification');
      this.activeModal.close();
    });
  }
}
