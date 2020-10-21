import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModuleCyber06 } from 'app/shared/model/module-cyber-06.model';
import { ModuleCyber06Service } from './module-cyber-06.service';

@Component({
  templateUrl: './module-cyber-06-delete-dialog.component.html',
})
export class ModuleCyber06DeleteDialogComponent {
  module?: IModuleCyber06;

  constructor(protected moduleService: ModuleCyber06Service, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moduleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('moduleListModification');
      this.activeModal.close();
    });
  }
}
