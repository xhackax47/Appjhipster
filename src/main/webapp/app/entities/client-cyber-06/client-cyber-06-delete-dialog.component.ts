import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientCyber06 } from 'app/shared/model/client-cyber-06.model';
import { ClientCyber06Service } from './client-cyber-06.service';

@Component({
  templateUrl: './client-cyber-06-delete-dialog.component.html',
})
export class ClientCyber06DeleteDialogComponent {
  client?: IClientCyber06;

  constructor(protected clientService: ClientCyber06Service, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clientService.delete(id).subscribe(() => {
      this.eventManager.broadcast('clientListModification');
      this.activeModal.close();
    });
  }
}
