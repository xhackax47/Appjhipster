import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppJhipsterTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ComposantCyber06DeleteDialogComponent } from 'app/entities/composant-cyber-06/composant-cyber-06-delete-dialog.component';
import { ComposantCyber06Service } from 'app/entities/composant-cyber-06/composant-cyber-06.service';

describe('Component Tests', () => {
  describe('ComposantCyber06 Management Delete Component', () => {
    let comp: ComposantCyber06DeleteDialogComponent;
    let fixture: ComponentFixture<ComposantCyber06DeleteDialogComponent>;
    let service: ComposantCyber06Service;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ComposantCyber06DeleteDialogComponent],
      })
        .overrideTemplate(ComposantCyber06DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComposantCyber06DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComposantCyber06Service);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
