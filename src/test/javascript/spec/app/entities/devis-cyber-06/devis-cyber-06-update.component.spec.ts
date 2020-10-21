import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { DevisCyber06UpdateComponent } from 'app/entities/devis-cyber-06/devis-cyber-06-update.component';
import { DevisCyber06Service } from 'app/entities/devis-cyber-06/devis-cyber-06.service';
import { DevisCyber06 } from 'app/shared/model/devis-cyber-06.model';

describe('Component Tests', () => {
  describe('DevisCyber06 Management Update Component', () => {
    let comp: DevisCyber06UpdateComponent;
    let fixture: ComponentFixture<DevisCyber06UpdateComponent>;
    let service: DevisCyber06Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [DevisCyber06UpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DevisCyber06UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DevisCyber06UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DevisCyber06Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DevisCyber06(123);
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
        const entity = new DevisCyber06();
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
