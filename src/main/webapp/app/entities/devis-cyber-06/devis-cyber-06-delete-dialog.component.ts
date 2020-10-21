import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevisCyber06 } from 'app/shared/model/devis-cyber-06.model';
import { DevisCyber06Service } from './devis-cyber-06.service';

@Component({
  templateUrl: './devis-cyber-06-delete-dialog.component.html',
})
export class DevisCyber06DeleteDialogComponent {
  devis?: IDevisCyber06;

  constructor(protected devisService: DevisCyber06Service, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.devisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('devisListModification');
      this.activeModal.close();
    });
  }
}
