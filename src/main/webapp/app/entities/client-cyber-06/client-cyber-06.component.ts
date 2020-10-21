import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClientCyber06 } from 'app/shared/model/client-cyber-06.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ClientCyber06Service } from './client-cyber-06.service';
import { ClientCyber06DeleteDialogComponent } from './client-cyber-06-delete-dialog.component';

@Component({
  selector: 'jhi-client-cyber-06',
  templateUrl: './client-cyber-06.component.html',
})
export class ClientCyber06Component implements OnInit, OnDestroy {
  clients: IClientCyber06[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected clientService: ClientCyber06Service,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.clients = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.clientService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IClientCyber06[]>) => this.paginateClients(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.clients = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClients();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClientCyber06): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClients(): void {
    this.eventSubscriber = this.eventManager.subscribe('clientListModification', () => this.reset());
  }

  delete(client: IClientCyber06): void {
    const modalRef = this.modalService.open(ClientCyber06DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.client = client;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateClients(data: IClientCyber06[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.clients.push(data[i]);
      }
    }
  }
}
