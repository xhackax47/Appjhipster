import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommercialCyber06 } from 'app/shared/model/commercial-cyber-06.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CommercialCyber06Service } from './commercial-cyber-06.service';
import { CommercialCyber06DeleteDialogComponent } from './commercial-cyber-06-delete-dialog.component';

@Component({
  selector: 'jhi-commercial-cyber-06',
  templateUrl: './commercial-cyber-06.component.html',
})
export class CommercialCyber06Component implements OnInit, OnDestroy {
  commercials: ICommercialCyber06[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected commercialService: CommercialCyber06Service,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.commercials = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.commercialService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ICommercialCyber06[]>) => this.paginateCommercials(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.commercials = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommercials();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommercialCyber06): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommercials(): void {
    this.eventSubscriber = this.eventManager.subscribe('commercialListModification', () => this.reset());
  }

  delete(commercial: ICommercialCyber06): void {
    const modalRef = this.modalService.open(CommercialCyber06DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commercial = commercial;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCommercials(data: ICommercialCyber06[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.commercials.push(data[i]);
      }
    }
  }
}
