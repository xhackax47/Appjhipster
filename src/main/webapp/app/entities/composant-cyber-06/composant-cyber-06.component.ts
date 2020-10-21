import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ComposantCyber06Service } from './composant-cyber-06.service';
import { ComposantCyber06DeleteDialogComponent } from './composant-cyber-06-delete-dialog.component';

@Component({
  selector: 'jhi-composant-cyber-06',
  templateUrl: './composant-cyber-06.component.html',
})
export class ComposantCyber06Component implements OnInit, OnDestroy {
  composants: IComposantCyber06[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected composantService: ComposantCyber06Service,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.composants = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.composantService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IComposantCyber06[]>) => this.paginateComposants(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.composants = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComposants();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComposantCyber06): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComposants(): void {
    this.eventSubscriber = this.eventManager.subscribe('composantListModification', () => this.reset());
  }

  delete(composant: IComposantCyber06): void {
    const modalRef = this.modalService.open(ComposantCyber06DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.composant = composant;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateComposants(data: IComposantCyber06[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.composants.push(data[i]);
      }
    }
  }
}
