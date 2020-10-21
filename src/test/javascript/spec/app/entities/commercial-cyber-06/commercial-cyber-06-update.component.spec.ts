import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { CommercialCyber06UpdateComponent } from 'app/entities/commercial-cyber-06/commercial-cyber-06-update.component';
import { CommercialCyber06Service } from 'app/entities/commercial-cyber-06/commercial-cyber-06.service';
import { CommercialCyber06 } from 'app/shared/model/commercial-cyber-06.model';

describe('Component Tests', () => {
  describe('CommercialCyber06 Management Update Component', () => {
    let comp: CommercialCyber06UpdateComponent;
    let fixture: ComponentFixture<CommercialCyber06UpdateComponent>;
    let service: CommercialCyber06Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [CommercialCyber06UpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommercialCyber06UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommercialCyber06UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommercialCyber06Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommercialCyber06(123);
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
        const entity = new CommercialCyber06();
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
