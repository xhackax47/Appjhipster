import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { ComposantCyber06DetailComponent } from 'app/entities/composant-cyber-06/composant-cyber-06-detail.component';
import { ComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';

describe('Component Tests', () => {
  describe('ComposantCyber06 Management Detail Component', () => {
    let comp: ComposantCyber06DetailComponent;
    let fixture: ComponentFixture<ComposantCyber06DetailComponent>;
    const route = ({ data: of({ composant: new ComposantCyber06(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ComposantCyber06DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ComposantCyber06DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComposantCyber06DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load composant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.composant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
