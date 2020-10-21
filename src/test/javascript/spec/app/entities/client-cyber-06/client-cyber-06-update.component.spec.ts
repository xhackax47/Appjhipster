import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { ClientCyber06UpdateComponent } from 'app/entities/client-cyber-06/client-cyber-06-update.component';
import { ClientCyber06Service } from 'app/entities/client-cyber-06/client-cyber-06.service';
import { ClientCyber06 } from 'app/shared/model/client-cyber-06.model';

describe('Component Tests', () => {
  describe('ClientCyber06 Management Update Component', () => {
    let comp: ClientCyber06UpdateComponent;
    let fixture: ComponentFixture<ClientCyber06UpdateComponent>;
    let service: ClientCyber06Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ClientCyber06UpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ClientCyber06UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClientCyber06UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientCyber06Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClientCyber06(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClientCyber06();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
