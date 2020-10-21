import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { DevisCyber06DetailComponent } from 'app/entities/devis-cyber-06/devis-cyber-06-detail.component';
import { DevisCyber06 } from 'app/shared/model/devis-cyber-06.model';

describe('Component Tests', () => {
  describe('DevisCyber06 Management Detail Component', () => {
    let comp: DevisCyber06DetailComponent;
    let fixture: ComponentFixture<DevisCyber06DetailComponent>;
    const route = ({ data: of({ devis: new DevisCyber06(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [DevisCyber06DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DevisCyber06DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DevisCyber06DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load devis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.devis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
