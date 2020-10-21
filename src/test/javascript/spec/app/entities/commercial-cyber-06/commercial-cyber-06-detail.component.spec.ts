import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { CommercialCyber06DetailComponent } from 'app/entities/commercial-cyber-06/commercial-cyber-06-detail.component';
import { CommercialCyber06 } from 'app/shared/model/commercial-cyber-06.model';

describe('Component Tests', () => {
  describe('CommercialCyber06 Management Detail Component', () => {
    let comp: CommercialCyber06DetailComponent;
    let fixture: ComponentFixture<CommercialCyber06DetailComponent>;
    const route = ({ data: of({ commercial: new CommercialCyber06(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [CommercialCyber06DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommercialCyber06DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommercialCyber06DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commercial on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commercial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
