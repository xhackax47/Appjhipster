import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { ModuleCyber06DetailComponent } from 'app/entities/module-cyber-06/module-cyber-06-detail.component';
import { ModuleCyber06 } from 'app/shared/model/module-cyber-06.model';

describe('Component Tests', () => {
  describe('ModuleCyber06 Management Detail Component', () => {
    let comp: ModuleCyber06DetailComponent;
    let fixture: ComponentFixture<ModuleCyber06DetailComponent>;
    const route = ({ data: of({ module: new ModuleCyber06(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ModuleCyber06DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ModuleCyber06DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModuleCyber06DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load module on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.module).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
