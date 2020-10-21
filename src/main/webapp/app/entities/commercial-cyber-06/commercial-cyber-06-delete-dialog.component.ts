import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialCyber06 } from 'app/shared/model/commercial-cyber-06.model';
import { CommercialCyber06Service } from './commercial-cyber-06.service';

@Component({
  templateUrl: './commercial-cyber-06-delete-dialog.component.html',
})
export class CommercialCyber06DeleteDialogComponent {
  commercial?: ICommercialCyber06;

  constructor(
    protected commercialService: CommercialCyber06Service,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commercialService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commercialListModification');
      this.activeModal.close();
    });
  }
}
