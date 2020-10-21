import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppJhipsterTestModule } from '../../../test.module';
import { ClientCyber06DetailComponent } from 'app/entities/client-cyber-06/client-cyber-06-detail.component';
import { ClientCyber06 } from 'app/shared/model/client-cyber-06.model';

describe('Component Tests', () => {
  describe('ClientCyber06 Management Detail Component', () => {
    let comp: ClientCyber06DetailComponent;
    let fixture: ComponentFixture<ClientCyber06DetailComponent>;
    const route = ({ data: of({ client: new ClientCyber06(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppJhipsterTestModule],
        declarations: [ClientCyber06DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ClientCyber06DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientCyber06DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load client on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.client).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
