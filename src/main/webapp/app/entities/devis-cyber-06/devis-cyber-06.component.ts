import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDevisCyber06 } from 'app/shared/model/devis-cyber-06.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DevisCyber06Service } from './devis-cyber-06.service';
import { DevisCyber06DeleteDialogComponent } from './devis-cyber-06-delete-dialog.component';

@Component({
  selector: 'jhi-devis-cyber-06',
  templateUrl: './devis-cyber-06.component.html',
})
export class DevisCyber06Component implements OnInit, OnDestroy {
  devis: IDevisCyber06[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected devisService: DevisCyber06Service,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.devis = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.devisService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IDevisCyber06[]>) => this.paginateDevis(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.devis = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDevis();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDevisCyber06): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDevis(): void {
    this.eventSubscriber = this.eventManager.subscribe('devisListModification', () => this.reset());
  }

  delete(devis: IDevisCyber06): void {
    const modalRef = this.modalService.open(DevisCyber06DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.devis = devis;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDevis(data: IDevisCyber06[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.devis.push(data[i]);
      }
    }
  }
}
