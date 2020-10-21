import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { ModuleCyber06UpdateComponent } from 'app/entities/module-cyber-06/module-cyber-06-update.component';
import { ModuleCyber06Service } from 'app/entities/module-cyber-06/module-cyber-06.service';
import { ModuleCyber06 } from 'app/shared/model/module-cyber-06.model';

describe('Component Tests', () => {
  describe('ModuleCyber06 Management Update Component', () => {
    let comp: ModuleCyber06UpdateComponent;
    let fixture: ComponentFixture<ModuleCyber06UpdateComponent>;
    let service: ModuleCyber06Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ModuleCyber06UpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ModuleCyber06UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModuleCyber06UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModuleCyber06Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModuleCyber06(123);
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
        const entity = new ModuleCyber06();
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
