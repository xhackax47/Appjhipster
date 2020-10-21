import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { ComposantCyber06UpdateComponent } from 'app/entities/composant-cyber-06/composant-cyber-06-update.component';
import { ComposantCyber06Service } from 'app/entities/composant-cyber-06/composant-cyber-06.service';
import { ComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';

describe('Component Tests', () => {
  describe('ComposantCyber06 Management Update Component', () => {
    let comp: ComposantCyber06UpdateComponent;
    let fixture: ComponentFixture<ComposantCyber06UpdateComponent>;
    let service: ComposantCyber06Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ComposantCyber06UpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ComposantCyber06UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComposantCyber06UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComposantCyber06Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComposantCyber06(123);
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
        const entity = new ComposantCyber06();
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
